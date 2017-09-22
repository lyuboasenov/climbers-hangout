package com.climbershangout.climbershangout;

import android.content.Context;
import android.content.SharedPreferences;

import com.climbershangout.climbershangout.settings.SettingsKeys;

/**
 * Created by lyuboslav on 9/22/2017.
 */

public class User {

    //Members
    private static User user;

    private Context context;
    private String username;
    private String email;
    private int loginType;
    private String token;

    //Constructors
    private User(Context context) {
        setContext(context);
        setUser(this);
    }

    //Properties
    public static User getUser(Context context) {
        if (null == user) {
            user = new User(context);
            user.load();
        }
        return user;
    }

    public static User getUser() {
        if (null == user) {
            throw new IllegalArgumentException("Context not initialized. Call the overload with context argument.");
        }
        return user;
    }

    private static void setUser(User user) {
        User.user = user;
    }

    public Context getContext() {
        return context;
    }

    private User setContext(Context context) {
        this.context = context;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public int getLoginType() {
        return loginType;
    }

    public User setLoginType(int loginType) {
        this.loginType = loginType;
        return this;
    }

    public String getToken() {
        return token;
    }

    public User setToken(String token) {
        this.token = token;
        return this;
    }

    //Methods
    public void save() {
        getSharedPreferences()
                .edit()
                .putString(SettingsKeys.User.TOKEN, getToken())
                .putString(SettingsKeys.User.USERNAME, getUsername())
                .putString(SettingsKeys.User.EMAIL, getEmail())
                .putInt(SettingsKeys.User.LOGIN_TYPE, getLoginType())
                .commit();
    }

    public void load() {
        SharedPreferences preferences = getSharedPreferences();
        this
                .setUsername(preferences.getString(SettingsKeys.User.USERNAME, ""))
                .setEmail(preferences.getString(SettingsKeys.User.EMAIL, ""))
                .setLoginType(preferences.getInt(SettingsKeys.User.LOGIN_TYPE, 0))
                .setToken(preferences.getString(SettingsKeys.User.TOKEN, ""));
    }

    public void signOut() {
        this
                .setUsername("")
                .setEmail("")
                .setLoginType(0)
                .setToken("")
                .save();

    }

    public boolean isLoggedIn() {
        return !getUsername().isEmpty();
    }

    private SharedPreferences getSharedPreferences(){
        return getContext().getSharedPreferences(SettingsKeys.SHARED_PREF_FILE, Context.MODE_PRIVATE);
    }
}
