package org.openmrs.client.test.robotium;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.jayway.android.robotium.solo.Solo;

import org.openmrs.client.activities.DashboardActivity;
import org.openmrs.client.activities.LoginActivity;
import org.openmrs.client.R;

public class LoginActivityTest extends
        ActivityInstrumentationTestCase2<LoginActivity> {

    private Solo solo;
    private static final String WRONG_SERVER_URL = "http://openmrs-ac-ci.soldevelo.com:8080/openmrs-standalone";
    private static final String WRONG_PASSWORD = "Testuser";
    private static final String EMPTY_FIELD = "Login and password can not be empty.";

    public LoginActivityTest() {
        super(LoginActivity.class);
    }

    @Override
    public void setUp() throws java.lang.Exception {
        super.setUp();

        solo = new Solo(getInstrumentation(), getActivity());
        getInstrumentation().waitForIdleSync();

        solo.waitForActivity(LoginActivity.class, 60000);
        solo.assertCurrentActivity("Wrong activity. LoginActivity expected", LoginActivity.class);
    }

    public void testEmptyBothFields() throws Exception {
        EditText loginUsernameField = (EditText) solo.getView(R.id.loginUsernameField);
        EditText loginPasswordField = (EditText) solo.getView(R.id.loginPasswordField);
        solo.clearEditText(loginUsernameField);
        solo.clearEditText(loginPasswordField);

        //Click on Login button
        assertTrue(WaitHelper.waitForText(solo, LoginHelper.LOGIN_BUTTON));
        solo.clickOnButton(LoginHelper.LOGIN_BUTTON);

        assertTrue(WaitHelper.waitForText(solo, EMPTY_FIELD));
    }

    public void testEmptyPassField() throws Exception {
        EditText loginUsernameField = (EditText) solo.getView(R.id.loginUsernameField);
        EditText loginPasswordField = (EditText) solo.getView(R.id.loginPasswordField);
        solo.clearEditText(loginUsernameField);
        solo.clearEditText(loginPasswordField);
        solo.enterText(loginUsernameField, LoginHelper.LOGIN);

        //Click on Login button
        assertTrue(WaitHelper.waitForText(solo, LoginHelper.LOGIN_BUTTON));
        solo.clickOnButton(LoginHelper.LOGIN_BUTTON);

        assertTrue(WaitHelper.waitForText(solo, EMPTY_FIELD));
    }

    public void testEmptyLoginField() throws Exception {
        EditText loginUsernameField = (EditText) solo.getView(R.id.loginUsernameField);
        EditText loginPasswordField = (EditText) solo.getView(R.id.loginPasswordField);
        solo.clearEditText(loginUsernameField);
        solo.clearEditText(loginPasswordField);
        solo.enterText(loginPasswordField, LoginHelper.PASSWORD);

        //Click on Login button
        assertTrue(WaitHelper.waitForText(solo, LoginHelper.LOGIN_BUTTON));
        solo.clickOnButton(LoginHelper.LOGIN_BUTTON);

        assertTrue(WaitHelper.waitForText(solo, EMPTY_FIELD));
    }

    public void testLogin() throws Exception {
        LoginHelper.login(solo);
        solo.waitForActivity(DashboardActivity.class, WaitHelper.ACTIVITY_TIMEOUT);
        solo.assertCurrentActivity("Wrong activity. DashboardActivity expected", DashboardActivity.class);
    }

    public void testLoginFailed() throws Exception {
        //Write login
        EditText loginUsernameField = (EditText) solo.getView(R.id.loginUsernameField);
        solo.clearEditText(loginUsernameField);
        solo.enterText(loginUsernameField, LoginHelper.LOGIN);

        //Write password
        EditText loginPasswordField = (EditText) solo.getView(R.id.loginPasswordField);
        solo.clearEditText(loginPasswordField);
        solo.enterText(loginPasswordField, WRONG_PASSWORD);

        //Click on Login button
        assertTrue(WaitHelper.waitForText(solo, LoginHelper.LOGIN_BUTTON));
        solo.clickOnButton(LoginHelper.LOGIN_BUTTON);

        //Write url
        EditText urlField = (EditText) solo.getView(R.id.openmrsEditText);
        solo.clearEditText(urlField);
        solo.enterText(urlField, LoginHelper.SERVER_URL);

        //Click on Done button
        assertTrue(WaitHelper.waitForText(solo, LoginHelper.DONE_BUTTON));
        solo.clickOnButton(LoginHelper.DONE_BUTTON);

        assertTrue(WaitHelper.waitForText(solo, "Your user name or password may be incorrect. Please try again."));
    }

    public void testWrongUrl() throws Exception {
        //Write login
        EditText loginUsernameField = (EditText) solo.getView(R.id.loginUsernameField);
        solo.clearEditText(loginUsernameField);
        solo.enterText(loginUsernameField, LoginHelper.LOGIN);

        //Write password
        EditText loginPasswordField = (EditText) solo.getView(R.id.loginPasswordField);
        solo.clearEditText(loginPasswordField);
        solo.enterText(loginPasswordField, LoginHelper.PASSWORD);

        //Click on Login button
        assertTrue(WaitHelper.waitForText(solo, LoginHelper.LOGIN_BUTTON));
        solo.clickOnButton(LoginHelper.LOGIN_BUTTON);

        //Write wrong url
        EditText urlField = (EditText) solo.getView(R.id.openmrsEditText);
        solo.clearEditText(urlField);
        solo.enterText(urlField, WRONG_SERVER_URL);

        //Click on Done button
        WaitHelper.waitForText(solo, LoginHelper.DONE_BUTTON);
        solo.clickOnButton(LoginHelper.DONE_BUTTON);

        assertTrue(WaitHelper.waitForText(solo, "Cancel"));
    }

    @Override
    public void tearDown() throws java.lang.Exception  {
        solo.finishOpenedActivities();
        super.tearDown();
    }
}
