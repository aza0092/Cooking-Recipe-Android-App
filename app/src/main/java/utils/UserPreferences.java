package utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import models.User;

public class UserPreferences {
    private static final String TAG = UserPreferences.class.getSimpleName();
    private static final String ID = "id";
    private static final String USERNAME = "username";
    private static final String FULLNAME = "fullname";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String FIRST_RUN = "firstRun";

    public static void saveCurrentUser(Context context, User currentUser) {
        if (currentUser != null) {
            PreferenceManager.getDefaultSharedPreferences(context)
                    .edit()
                    .putInt(ID, currentUser.getId())
                    .putString(USERNAME, currentUser.getUsername())
                    .putString(FULLNAME, currentUser.getFullname())
                    .putString(EMAIL, currentUser.getEmail())
                    .putString(PASSWORD, currentUser.getPassword())
                    .apply();
        } else {
            Log.i(TAG, "cannot save current user: user is NULL.");
        }
    }

    public static boolean isUserLoggedIn(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .contains(ID);
    }

    public static User loadUser(Context context) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        if (sharedPreferences.contains(ID)) {
            return new User(
                    sharedPreferences.getInt(ID, -1),
                    sharedPreferences.getString(USERNAME, ""),
                    sharedPreferences.getString(FULLNAME, ""),
                    sharedPreferences.getString(EMAIL, ""),
                    sharedPreferences.getString(PASSWORD, "")
            );
        }

        return null;
    }

    public static void setIsFirstRun(Context context, boolean isFirstRun) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(FIRST_RUN, isFirstRun)
                .apply();
    }

    public static boolean isFirstRun(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(FIRST_RUN, true);
    }

    public static void clear(Context context) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .remove(ID)
                .remove(USERNAME)
                .remove(FULLNAME)
                .remove(EMAIL)
                .remove(PASSWORD)
                .apply();
    }
}
