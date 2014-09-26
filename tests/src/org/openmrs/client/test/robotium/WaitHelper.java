package org.openmrs.client.test.robotium;

import android.util.Log;

import com.jayway.android.robotium.solo.Solo;

public final class WaitHelper {

    private static final long TIMEOUT = 30000;
    private static final long MAX_TIMEOUT = 240000;
    public static final int ACTIVITY_TIMEOUT = 120000;

    private static final String TAG = "OpenMRSTest";
    private static final String TIME = "Time: ";
    private static final String RESULT = ", Result: ";

    private WaitHelper() {
    }

    public static boolean waitForText(Solo solo, String txt) throws java.lang.Exception {
        boolean result = false;
        int sec = 0;
        while (!result && sec * 1000 < MAX_TIMEOUT) {
            result = solo.waitForText(txt, 1, TIMEOUT);
            sec = sec + (int) TIMEOUT / 1000;
            Log.d(TAG, TIME + sec + "s., WaitForText: " + txt + RESULT + result);
        }
        return result;
    }

    public static boolean waitForText(Solo solo, String txt, long timeout) throws java.lang.Exception {
        boolean result = false;
        int sec = 0;
        while (!result && sec * 1000 < timeout) {
            result = solo.waitForText(txt, 1, TIMEOUT);
            sec = sec + (int) TIMEOUT / 1000;
            Log.d(TAG, TIME + sec + "s., WaitForText: " + txt + RESULT + result);
        }
        return result;
    }

    public static boolean waitForActivity(Solo solo, java.lang.Class<? extends android.app.Activity> activityClass) throws java.lang.Exception {
        boolean result = false;
        int sec = 0;
        while (!result && sec * 1000 < MAX_TIMEOUT) {
            result = solo.waitForActivity(activityClass, (int) TIMEOUT);
            sec = sec + (int) TIMEOUT / 1000;
            Log.d(TAG, TIME + sec + "s., WaitForActivity: " + activityClass + RESULT + result);
        }
        return result;
    }

    public static boolean waitForActivity(Solo solo, java.lang.Class<? extends android.app.Activity> activityClass, long timeout) throws java.lang.Exception {
        boolean result = false;
        int sec = 0;
        while (!result && sec * 1000 < timeout) {
            result = solo.waitForActivity(activityClass, (int) TIMEOUT);
            sec = sec + (int) TIMEOUT / 1000;
            Log.d(TAG, TIME + sec + "s., WaitForActivity: " + activityClass + RESULT + result);
        }
        return result;
    }

}
