package org.openmrs.client.test.robotium;


import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;

import org.openmrs.client.activities.DashboardActivity;
import org.openmrs.client.activities.LoginActivity;
import org.openmrs.client.activities.SettingsActivity;

public class LogoutTest extends ActivityInstrumentationTestCase2<DashboardActivity> {

    private Solo solo;

    public LogoutTest() {
        super(DashboardActivity.class);
    }

    @Override
    public void setUp() throws java.lang.Exception {
        super.setUp();

        solo = new Solo(getInstrumentation());
        getActivity();
        getInstrumentation().waitForIdleSync();
        if (WaitHelper.waitForActivity(solo, LoginActivity.class)) {
            LoginHelper.login(solo);
        }
    }

    public void testLogout() throws Exception {
        solo.waitForActivity(DashboardActivity.class, WaitHelper.ACTIVITY_TIMEOUT);
        solo.assertCurrentActivity("Wrong activity. DashboardActivity expected", DashboardActivity.class);

        //open menu
        solo.clickOnMenuItem("Settings");

        assertTrue(WaitHelper.waitForActivity(solo, SettingsActivity.class));
        solo.assertCurrentActivity("Wrong activity. SettingsActivity expected", SettingsActivity.class);

        solo.clickInList(3);

        //wait for Logout dialog
        assertTrue(WaitHelper.waitForText(solo, "Logging out"));

        //Click on Cancel button
        assertTrue(WaitHelper.waitForText(solo, "Cancel"));
        solo.clickOnButton("Cancel");

        solo.assertCurrentActivity("Wrong activity. SettingsActivity expected", SettingsActivity.class);

        solo.clickInList(3);

        //wait for Logout dialog
        assertTrue(WaitHelper.waitForText(solo, "Logging out"));

        //Click on Logout button
        assertTrue(WaitHelper.waitForText(solo, "Logout"));
        solo.clickOnButton("Logout");

        assertTrue(WaitHelper.waitForActivity(solo, LoginActivity.class));
        solo.assertCurrentActivity("Wrong activity. LoginActivity expected", LoginActivity.class);
    }

    @Override
    public void tearDown() throws java.lang.Exception  {
        solo.finishOpenedActivities();
        super.tearDown();
    }
}
