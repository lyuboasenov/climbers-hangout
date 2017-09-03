package com.climbershangout.climbershangout;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.felhr.usbserial.UsbSerialDevice;
import com.felhr.usbserial.UsbSerialInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by lyuboslav on 1/28/2017.
 */

public class BoardManager {

    private static final BoardManager boardManager = new BoardManager();
    private static final String ACTION_USB_PERMISSION = "com.climbershangout.ACTION_USB_PERMISION";
    private final int VID = 0x2341;

    private Context context;
    private UsbManager usbManager;

    private boolean isCheckingDevices = false;
    private boolean isListeningUsb = false;

    private boolean isBoardAttached = false;

    private static final Object[] sSendLock = new Object[]{};//learned this trick from some google example :)
    //basically an empty array is lighter than an  actual new Object()...

    private List<IBoardCallback> callbackList = new ArrayList<>();

    private BroadcastReceiver permissionReceiver = new PermissionReceiver(
            new IPermissionListener() {
                @Override
                public void onPermissionDenied(UsbDevice d) {
                    showToast("Permission denied on " + d.getDeviceId(), Toast.LENGTH_SHORT);
                }
            });

    private BoardManager() {

    }

    public static BoardManager getBoardManager() {
        return boardManager;
    }

    public void initialize(Context context1) {
        this.context = context1;
        this.usbManager = (UsbManager) this.context.getSystemService(Context.USB_SERVICE);

        startCheckingForDevices();
    }

    private void stopCheckingForDevices() {
        isCheckingDevices = false;
    }

    private void startCheckingForDevices() {
        isCheckingDevices = true;

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(isCheckingDevices) {
                    checkDevices();
                    try {
                        Thread.sleep(5 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void checkDevices() {
        enumerate(new IPermissionListener() {
            @Override
            public void onPermissionDenied(UsbDevice d) {
                UsbManager usbman = (UsbManager) context.getSystemService(Context.USB_SERVICE);
                PendingIntent pi = PendingIntent.getBroadcast(context, 0, new Intent(ACTION_USB_PERMISSION), 0);
                context.registerReceiver(permissionReceiver, new IntentFilter(ACTION_USB_PERMISSION));
                usbman.requestPermission(d, pi);
            }
        });
    }

    public void addBoardCallback(IBoardCallback callback) {
        if(!callbackList.contains(callback)) {
            callbackList.add(callback);
        }
    }

    public void removeBoardCallback(IBoardCallback callback) {
        if(callbackList.contains(callback)) {
            callbackList.remove(callback);
        }
    }

    private void invokeOnDataReceivedCallbacks(byte[] bytes) {
        if (null != callbackList) {
            for (IBoardCallback callback : callbackList) {
                callback.onReceivedData(bytes);
            }
        }
    }

    private void invokeOnAttachedDetachedCallbacks(boolean attachedDetached) {
        setBoardAttached(attachedDetached);
        if (null != callbackList) {
            for (IBoardCallback callback : callbackList) {
                callback.onBoardAttachedDetached(attachedDetached);
            }
        }
    }

    private void closeConnection() {
        if(isListeningUsb) {
            isListeningUsb = false;
        }
    }

    private void openConnection(final UsbDevice device) {

        invokeOnAttachedDetachedCallbacks(true);

        if (!isListeningUsb) {
            isListeningUsb = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    UsbDeviceConnection conn = usbManager.openDevice(device);
                    UsbSerialDevice serialPort = UsbSerialDevice.createUsbSerialDevice(device, conn);
                    if (serialPort != null) {
                        if (serialPort.open()) { //Set Serial Connection Parameters.
                            serialPort.setBaudRate(38400);
                            serialPort.setDataBits(UsbSerialInterface.DATA_BITS_8);
                            serialPort.setStopBits(UsbSerialInterface.STOP_BITS_1);
                            serialPort.setParity(UsbSerialInterface.PARITY_NONE);
                            serialPort.setFlowControl(UsbSerialInterface.FLOW_CONTROL_OFF);
                            serialPort.read(new UsbSerialInterface.UsbReadCallback() {
                                @Override
                                public void onReceivedData(byte[] bytes) {
                                    invokeOnDataReceivedCallbacks(bytes);
                                }
                            });

                            while (true) {// this is the main loop for transferring
                                if (!isListeningUsb) {
                                    serialPort.close();
                                    startCheckingForDevices();
                                    invokeOnAttachedDetachedCallbacks(false);
                                    return;
                                }

                                try {
                                    Thread.sleep(1 * 1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            showToast("Port not open!", Toast.LENGTH_SHORT);
                        }
                    } else {
                        showToast("Port is null!", Toast.LENGTH_SHORT);
                    }
                }
            }).start();
        }
    }

    private void enumerate(IPermissionListener listener) {
        if(!isListeningUsb) {
            HashMap<String, UsbDevice> devlist = usbManager.getDeviceList();
            Iterator<UsbDevice> deviter = devlist.values().iterator();
            while (deviter.hasNext()) {
                UsbDevice d = deviter.next();
                if (d.getVendorId() == VID) {
                    showToast("Device under: " + d.getDeviceName(), Toast.LENGTH_SHORT);
                    if (!usbManager.hasPermission(d)) {
                        listener.onPermissionDenied(d);
                        stopCheckingForDevices();
                    } else {
                        openConnection(d);
                        return;
                    }
                    break;
                }
            }
        }
    }

    private void showToast(final String message, final int duration) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, message, duration).show();
            }
        });
    }

    public boolean isBoardAttached() {
        return isBoardAttached;
    }

    private void setBoardAttached(boolean boardAttached) {
        isBoardAttached = boardAttached;
    }

    private class PermissionReceiver extends BroadcastReceiver {
        private final IPermissionListener permissionListener;

        public PermissionReceiver(IPermissionListener permissionListener) {
            this.permissionListener = permissionListener;
        }

        @Override
        public void onReceive(Context context1, Intent intent) {
            context.unregisterReceiver(this);
            if (intent.getAction().equals(ACTION_USB_PERMISSION)) {
                if (!intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                    permissionListener.onPermissionDenied((UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE));
                } else {
                    showToast("Permission granted!", Toast.LENGTH_SHORT);
                    UsbDevice dev = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (dev != null) {
                        if (dev.getVendorId() == VID) {
                            openConnection(dev);// has new thread
                        }
                    } else {
                        showToast("Device not found!", Toast.LENGTH_SHORT);
                    }
                }
            }
        }

    }

    private interface IPermissionListener {
        void onPermissionDenied(UsbDevice d);
    }

    public interface IBoardCallback {
        void onReceivedData(byte[] bytes);
        void onBoardAttachedDetached(boolean boardAttachedDetached);
    }
}
