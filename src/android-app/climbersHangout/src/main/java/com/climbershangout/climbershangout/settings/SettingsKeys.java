package com.climbershangout.climbershangout.settings;

/**
 * Created by lyuboslav on 2/4/2017.
 */

public class SettingsKeys {

    public static final String SHARED_PREF_FILE = "com.climbershangout.climbershangout";

    public class Main {
        public static final String THEME = "settings_main_theme";
    }

    public class User {
        public static final String USERNAME = "settings_user_username";
        public static final String EMAIL = "settings_user_email";
        public static final String LOGIN_TYPE = "settings_user_login_type";
    }

    public class Notifications {
        public static final String VIBRATE = "settings_notification_vibrate";
        public static final String PREP = "settings_notification_prep";
        public static final String WORK = "settings_notification_work";
        public static final String REST = "settings_notification_rest";
        public static final String PAUSE = "settings_notification_pause";
        public static final String PREP_TIME = "settings_notification_prep_time";
        public static final String WORK_TIME = "settings_notification_work_time";
        public static final String REST_TIME = "settings_notification_rest_time";
        public static final String PAUSE_TIME = "settings_notification_pause_time";
        public static final String TONE_V0LUME = "settings_notification_tone_volume";
    }

}
