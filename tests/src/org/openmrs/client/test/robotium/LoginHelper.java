package org.openmrs.client.test.robotium;

import android.widget.EditText;

import com.jayway.android.robotium.solo.Solo;

import static junit.framework.Assert.assertTrue;

public final class LoginHelper {

    public static final String LOGIN = "test1";
    public static final String PASSWORD = "Testuser1";
    public static final String SERVER_URL = "http://openmrs-ac-ci.soldevelo.com:8081/openmrs-standalone";
    public static final String LOGIN_BUTTON = "Login";
    public static final String DONE_BUTTON = "Done";

    private LoginHelper() {
    }

    public static void login(Solo solo) throws java.lang.Exception {
        //Write login
        EditText loginUsernameField = (EditText) solo.getView(org.openmrs.client.R.id.loginUsernameField);
        solo.clearEditText(loginUsernameField);
        solo.enterText(loginUsernameField, LOGIN);

        //Write password
        EditText loginPasswordField = (EditText) solo.getView(org.openmrs.client.R.id.loginPasswordField);
        solo.clearEditText(loginPasswordField);
        solo.enterText(loginPasswordField, PASSWORD);

        //Click on Login button
        assertTrue(WaitHelper.waitForText(solo, LOGIN_BUTTON));
        solo.clickOnButton(LOGIN_BUTTON);

        //Write url
        EditText urlField = (EditText) solo.getView(org.openmrs.client.R.id.openmrsEditText);
        solo.clearEditText(urlField);
        solo.enterText(urlField, SERVER_URL);

        //Click on Done button
        assertTrue(WaitHelper.waitForText(solo, DONE_BUTTON));
        solo.clickOnButton(DONE_BUTTON);

        //return WaitHelper.waitForText(solo, "Login successful");
    }
}
