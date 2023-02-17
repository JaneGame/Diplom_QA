package ru.iteco.fmhandroid.ui;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static ru.iteco.fmhandroid.ui.Help.auth;
import static ru.iteco.fmhandroid.ui.Help.checkIfLogin;
import static ru.iteco.fmhandroid.ui.Help.createClaimBefore;
import static ru.iteco.fmhandroid.ui.Help.openClaim;
import static ru.iteco.fmhandroid.ui.WaitId.waitId;

import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.appcompat.widget.MenuPopupWindow;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDateTime;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;

import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.Menu.*;

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
        createClaimBefore();
        Thread.sleep(5000);
        onView(ViewMatchers.withId(R.id.claim_list_recycler_view))
                .perform(RecyclerViewActions.scrollTo(hasDescendant(withText(text))))
                .perform(click());
        onView(withText(R.string.in_progress)).check(matches(isDisplayed()));
    }
}
