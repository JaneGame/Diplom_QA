package ru.iteco.fmhandroid.ui;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static ru.iteco.fmhandroid.ui.Help.auth;
import static ru.iteco.fmhandroid.ui.Help.checkIfLogin;
import static ru.iteco.fmhandroid.ui.Help.openClaim;

import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.appcompat.widget.MenuPopupWindow;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
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
        onView(withId(R.id.add_new_claim_material_button)).perform(click());
        LocalDateTime now = LocalDateTime.now();
        onView(withId(R.id.title_edit_text)).perform(typeText("Test" + now));
        //onView(withId(R.id.executor_drop_menu_auto_complete_text_view)).perform(click());
        //onView(withText("Ivanov Ivan Ivanovich")).perform(click());
        onView(withId(R.id.date_in_plan_text_input_layout)).perform(click());
        onView(isAssignableFrom(DatePicker.class))
                .perform(PickerActions.setDate(
                        now.getYear(),
                        now.getMonthValue(),
                        now.getDayOfMonth())
                );

        onView(withText(android.R.string.ok)).perform(click());

        onView(withId(R.id.time_in_plan_text_input_layout)).perform(click());

        onView(isAssignableFrom(TimePicker.class))
                .perform(PickerActions.setTime(
                        now.getHour(),
                        now.getMinute())
                );
        onView(withText(android.R.string.ok)).perform(click());

        onView(withId(R.id.description_edit_text)).perform(typeText("Hi"+now), closeSoftKeyboard());

        onView(withId(R.id.save_button)).perform(click());

        onView(withText("Test" + now)).perform(scrollTo());
        Thread.sleep(5000);
    }
}
