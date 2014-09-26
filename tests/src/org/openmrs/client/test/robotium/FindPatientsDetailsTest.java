package org.openmrs.client.test.robotium;

import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.widget.CheckBox;

import com.jayway.android.robotium.solo.Solo;

import org.openmrs.client.activities.FindPatientsActivity;
import org.openmrs.client.R;
import org.openmrs.client.activities.FindPatientsSearchActivity;
import org.openmrs.client.activities.LoginActivity;

public class FindPatientsDetailsTest extends
        ActivityInstrumentationTestCase2<FindPatientsActivity> {

    private Solo solo;
    private static final String PATIENT_EXIST = "Armstrong";
    private static final String PATIENT_NO_EXIST = "Enrique";
    private static final String MESSAGE = "Wrong activity. FindPatientsActivity expected";
    private static boolean isAuthenticated;

    public FindPatientsDetailsTest() {
        super(FindPatientsActivity.class);
    }

    @Override
    public void setUp() throws java.lang.Exception {
        super.setUp();
        //if (!isAuthenticated) {
        //    getInstrumentation().getTargetContext().deleteDatabase(OpenMRSSQLiteOpenHelper.DATABASE_NAME);
        //}
        solo = new Solo(getInstrumentation());
        getActivity();
        getInstrumentation().waitForIdleSync();
        if (!isAuthenticated) {
            if (!solo.waitForActivity(LoginActivity.class, WaitHelper.ACTIVITY_TIMEOUT)) {
                ((FindPatientsActivity) solo.getCurrentActivity()).moveUnauthorizedUserToLoginScreen();
            }
            solo.waitForActivity(LoginActivity.class, WaitHelper.ACTIVITY_TIMEOUT);
            solo.assertCurrentActivity("Wrong activity. LoginActivity expected", LoginActivity.class);

            LoginHelper.login(solo);
            isAuthenticated = true;
            solo.waitForActivity(FindPatientsActivity.class, WaitHelper.ACTIVITY_TIMEOUT);
        }
    }

    public void testPatientNotExist() throws Exception {
        solo.assertCurrentActivity(MESSAGE, FindPatientsActivity.class);

        SearchHelper.doSearch(solo, getInstrumentation(), PATIENT_NO_EXIST, "Patient name");

        solo.waitForActivity(FindPatientsSearchActivity.class, WaitHelper.ACTIVITY_TIMEOUT);
        solo.assertCurrentActivity("Wrong activity. FindPatientsSearchActivity expected", FindPatientsSearchActivity.class);

        assertTrue(WaitHelper.waitForText(solo, "No results found for query \"" +  PATIENT_NO_EXIST + "\""));

        solo.goBack();
        solo.waitForActivity(FindPatientsActivity.class, WaitHelper.ACTIVITY_TIMEOUT);
        solo.assertCurrentActivity("Wrong activity. FindPatientsActivity expected", FindPatientsActivity.class);
    }

    public void testSearchPatient() throws Exception {
        solo.assertCurrentActivity(MESSAGE, FindPatientsActivity.class);

        SearchHelper.doSearch(solo, getInstrumentation(), PATIENT_EXIST, "Patient name");

        solo.waitForActivity(FindPatientsSearchActivity.class, WaitHelper.ACTIVITY_TIMEOUT);
        solo.assertCurrentActivity("Wrong activity. FindPatientsSearchActivity expected", FindPatientsSearchActivity.class);

        assertTrue(WaitHelper.waitForText(solo, PATIENT_EXIST));

        solo.goBack();
        solo.waitForActivity(FindPatientsActivity.class, WaitHelper.ACTIVITY_TIMEOUT);
        solo.assertCurrentActivity("Wrong activity. FindPatientsActivity expected", FindPatientsActivity.class);
    }

    public void testSearchPatientAndSave() throws Exception {
        solo.assertCurrentActivity(MESSAGE, FindPatientsActivity.class);

        SearchHelper.doSearch(solo, getInstrumentation(), PATIENT_EXIST, "Patient name");

        solo.waitForActivity(FindPatientsSearchActivity.class, WaitHelper.ACTIVITY_TIMEOUT);
        solo.assertCurrentActivity("Wrong activity. FindPatientsSearchActivity expected", FindPatientsSearchActivity.class);

        assertTrue(WaitHelper.waitForText(solo, PATIENT_EXIST));

        CheckBox isPatientSave = (CheckBox) solo.getView(R.id.offlineCheckbox);
        assertTrue(WaitHelper.waitForText(solo, "Download"));
        assertFalse(isPatientSave.isChecked());

        solo.clickOnCheckBox(0);

        assertTrue(WaitHelper.waitForText(solo, "Available offline"));

        getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);

        solo.waitForActivity(FindPatientsActivity.class, WaitHelper.ACTIVITY_TIMEOUT);

        assertTrue(WaitHelper.waitForText(solo, PATIENT_EXIST));
    }

    @Override
    public void tearDown() throws java.lang.Exception  {
        solo.finishOpenedActivities();
        super.tearDown();
    }

}
