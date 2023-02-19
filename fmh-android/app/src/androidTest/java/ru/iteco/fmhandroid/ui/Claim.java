package ru.iteco.fmhandroid.ui;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static ru.iteco.fmhandroid.ui.Help.Helps.auth;
import static ru.iteco.fmhandroid.ui.Help.Helps.checkIfLogin;
import static ru.iteco.fmhandroid.ui.Help.Helps.workPopup;
import static ru.iteco.fmhandroid.ui.Help.WaitId.waitId;
import static ru.iteco.fmhandroid.ui.Help.WorkClaim.createClaimAfter;
import static ru.iteco.fmhandroid.ui.Help.WorkClaim.createClaimBefore;
import static ru.iteco.fmhandroid.ui.Help.WorkClaim.foundClaim;
import static ru.iteco.fmhandroid.ui.Help.WorkMenu.openClaim;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDateTime;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;

import ru.iteco.fmhandroid.R;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class Claim {

    @Rule
    public ActivityTestRule<AppActivity> mActivityTestRule = new ActivityTestRule<>(AppActivity.class);

    @Before
    public void loginIfrequired() {
        if (!checkIfLogin()) {
            auth("login2", "password2");
        }
        openClaim();
    }

    @Test

    public void executor() throws InterruptedException {
        LocalDateTime now = LocalDateTime.now();
        String text = "Test" + now;
        createClaimBefore(text);
        onView(withId(R.id.executor_drop_menu_auto_complete_text_view)).perform(click());
        workPopup("Ivanov Ivan Ivanovich");
        createClaimAfter(now);
        foundClaim(text);
//        onView(isRoot()).perform(waitId(R.id.status_icon_image_view, 10000)).check(matches(isDisplayed()));
        Thread.sleep(2000);
        onView(withText(R.string.in_progress)).check(matches(isDisplayed()));
    }

    @Test

    public void noExecutor() throws InterruptedException {
        LocalDateTime now = LocalDateTime.now();
        String text = "Test" + now;
        createClaimBefore(text);
        createClaimAfter(now);
        foundClaim(text);
 //       onView(isRoot()).perform(waitId(R.id.status_icon_image_view, 10000)).check(matches(isDisplayed()));
        Thread.sleep(2000);
        onView(withText(R.string.open)).check(matches(isDisplayed()));
    }
    @Test

    public void filterClaim(){
        LocalDateTime now = LocalDateTime.now();
        String text = "Test" + now;
        createClaimBefore(text);
        createClaimAfter(now);
        onView(withId(R.id.filters_material_button)).perform(click());
        onView(withId(R.id.item_filter_in_progress)).perform(click());
        onView(withId(R.id.claim_list_filter_ok_material_button)).perform(click());
        foundClaim(text);
    }

    @Test
    public void openToClose() throws InterruptedException {
        LocalDateTime now = LocalDateTime.now();
        String text = "Test" + now;
        createClaimBefore(text);
        createClaimAfter(now);
        foundClaim(text);
        onView(withId(R.id.status_processing_image_button)).perform(click());
        workPopup("Cancel");
 //       onView(isRoot()).perform(waitId(R.id.status_icon_image_view, 10000)).check(matches(isDisplayed()));
        Thread.sleep(2000);
        onView(withText(R.string.status_claim_canceled)).check(matches(isDisplayed()));
    }

    @Test
    public void openToWork() throws InterruptedException {
        LocalDateTime now = LocalDateTime.now();
        String text = "Test" + now;
        createClaimBefore(text);
        createClaimAfter(now);
        foundClaim(text);
        onView(withId(R.id.status_processing_image_button)).perform(click());
        workPopup("take to work");
  //      onView(isRoot()).perform(waitId(R.id.status_icon_image_view, 10000)).check(matches(isDisplayed()));
        Thread.sleep(2000);
        onView(withText(R.string.in_progress)).check(matches(isDisplayed()));
    }

    @Test
    public void editClaim() throws InterruptedException {
        LocalDateTime now = LocalDateTime.now();
        String text = "Test" + now;
        createClaimBefore(text);
        createClaimAfter(now);
        foundClaim(text);
        onView(withId(R.id.edit_processing_image_button)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.executor_drop_menu_auto_complete_text_view)).perform(click());
        workPopup("Ivanov Ivan Ivanovich");
        onView(withId(R.id.executor_drop_menu_auto_complete_text_view)).perform(closeSoftKeyboard());
        onView(withId(R.id.save_button)).perform(click());
   //     onView(isRoot()).perform(waitId(R.id.status_icon_image_view, 10000)).check(matches(isDisplayed()));
        Thread.sleep(2000);
        onView(withText(R.string.in_progress)).check(matches(isDisplayed()));
    }
}
