package com.climbershangout.climbershangout.common;

public interface Camera {
    void takePhoto();
    void addListener(CameraListener listener);
    void removeListener(CameraListener listener);
}
