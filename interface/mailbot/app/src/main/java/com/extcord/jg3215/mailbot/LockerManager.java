package com.extcord.jg3215.mailbot;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by IChinweze on 22/11/2018.
 * LockerManager objects allow manipulation of a seven digit string used to represent locker state
 * from any activity. The digits are stored in a file that can be accessed from each activity (called
 * a sharedPreferences file). There are seven lockers so each digit refers to a specific locker.
 * There are 4 small lockers, 2 medium lockers and 1 large one.
 */

public class LockerManager {

    private final static String TAG = "LockerManager";

    // Gets the activity that this object belongs to
    Context mContext;

    // Used to access file that stores locker state data
    SharedPreferences sharedPreferences;
    String filename = "lockerPrefs";

    // Preferences are stored in key-value pairs. Key is used to retrieve availability string
    String key = "key";

    // Variable that will contain the string representing locker state
    String lockerState;

    // Edit the contents of the sharedPreference file
    SharedPreferences.Editor editor;

    // Registers whether or not the app is being used in test mode or nah
    // NOTE: SharedPreferences will remain 0000000 unless this is false
    boolean testMode;

    // Get the locker that has been chosen for mail item
    int selectLockerIndex;

    public LockerManager(Context context, boolean mode) {
        mContext = context;
        testMode = mode;

        // TODO: Check that sharedPreferences are shared on an application-wide scale rather than activity-wide
        sharedPreferences = mContext.getSharedPreferences(filename, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (sharedPreferences.getString(key, null) == null || testMode) {
            if (testMode) {
                // Locker state string variable set to 0000000 in the constructor of LockerManager object
                lockerState = "0000000";

                String currentState = sharedPreferences.getString(key, null);
                if (currentState != null && currentState.equals(lockerState)) {
                    Log.i(TAG, "LockerManager constructor: lockerState string already set to default");
                } else {
                    // TODO: Check that creating multiple locker manager objects does not create multiple sharedPreference files
                    Log.i(TAG, "LockerManager constructor: Putting " + lockerState + " to sharedPreference file");

                    editor.putString(key, lockerState);

                    // Commit changes to the sharedPreference file
                    editor.apply();
                }
            }
        } else {
            Log.i(TAG, "LockerManager constructor: sharedPreference file contains states");
            lockerState = sharedPreferences.getString(key, null);
            Log.i(TAG, "LockerManager constructor: lockerState = " + lockerState);
        }
    }

    // Gets the data from the availability string
    public int getAvailability(int packageType) {

        Log.i(TAG, "getAvailability() method called");

        if (lockerState != null) {
            Log.i(TAG, "lockerState not null");

            // Counts how many lockers are available
            int aCount = 0;

            // 4 small, 2 medium, 1 big
            if (packageType == 1) {
                for (int i = 0; i < 4; i++) {
                    if (lockerState.charAt(i) == '0') {
                        // if the character at that index is '0', the locker is free
                        aCount++;
                    }
                }

                Log.i(TAG, "Lockers available: " + String.valueOf(aCount));
                return aCount;
            } else if(packageType == 2) {
                for (int i = 4; i < 6; i++) {
                    if (lockerState.charAt(i) == '0') {
                        aCount++;
                    }
                }

                Log.i(TAG, "Lockers available: " + String.valueOf(aCount));
                return aCount;
            } else {
                if (lockerState.charAt(6) == '0') {
                    aCount++;
                }

                Log.i(TAG, "Lockers available: " + String.valueOf(aCount));
                return aCount;
            }

        } else {
            // Might need to throw an exception
            Log.i(TAG, "sharedPreference file does not contain any values at this key");
        }

        // should not really get down here
        return 0;
    }

    // Updates the availability string
    public void updateAvailability(int lockerIndex, boolean isInserting) {

        Log.i(TAG, "updateAvailability() method called");
        StringBuilder changeLockerState = new StringBuilder(lockerState);
        Log.i(TAG, "isInserting = " + String.valueOf(isInserting) + " at locker index = " + String.valueOf(lockerIndex));

        if (isInserting) {
            // Is inserting a mail item into the locker
            changeLockerState.setCharAt(lockerIndex, '1');
        } else {
            // Is removing a mail item from the locker
            changeLockerState.setCharAt(lockerIndex, '0');
        }

        // Update lockerState string
        lockerState = changeLockerState.toString();

        Log.i(TAG, "Writing " + lockerState + " to sharedPreferences file");
        editor.putString(key, lockerState);
        editor.apply();
    }

    public void setSelectLockerIndex(int packageType) {
        Log.i(TAG, "setSelectLockerIndex() method called");

        switch (packageType) {
            case 1:
                for (int i = 0; i < 4; i++) {
                    if (lockerState.charAt(i) == '0') {
                        selectLockerIndex = i;
                        Log.i(TAG, "setSelectLockerIndex(): Locker available at index " + String.valueOf(i));
                        // Gets the first index at which locker state is 'free' for that mail item
                        break;
                    }
                }
                break;
            case 2:
                for (int i = 4; i < 6; i++) {
                    if (lockerState.charAt(i) == '0') {
                        selectLockerIndex = i;
                        Log.i(TAG, "setSelectLockerIndex(): Locker available at index " + String.valueOf(i));
                        // Gets the first index at which locker state is 'free' for that mail item
                        break;
                    }
                }
                break;
            case 3:
                // should only call this function if a large locker is available so if-statement is redundant
                Log.i(TAG, "setSelectLockerIndex(): Locker available at index 6");
                selectLockerIndex = 6;
                break;
        }
    }

    public int getSelectLockerIndex() { return this.selectLockerIndex; }
}
