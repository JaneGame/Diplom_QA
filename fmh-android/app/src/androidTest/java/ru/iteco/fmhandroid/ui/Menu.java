package ru.iteco.fmhandroid.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static ru.iteco.fmhandroid.ui.Help.checkIfLogin;
import static ru.iteco.fmhandroid.ui.WaitId.waitId;

import android.widget.DatePicker;
import android.widget.EditText;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDateTime;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import ru.iteco.fmhandroid.R;

import static ru.iteco.fmhandroid.ui.Help.*;


@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class Menu {


        @Rule
        public ActivityTestRule<AppActivity> mActivityTestRule = new ActivityTestRule<>(AppActivity.class);

        @Before
        public void loginIfrequired() {
            if (!checkIfLogin()) {
                auth("login2", "password2");
        }}



        @Test

            public void menuClaims(){
            openClaim();
            onView(withId(R.id.claim_list_recycler_view)).check(matches(isDisplayed()));
        }

        @Test

        public void menuNews(){
            authGood();
            onView(withId(R.id.main_menu_image_button)).perform(click());
            onView(withText(R.string.news)).perform(click());
            onView(withId(R.id.all_news_cards_block_constraint_layout)).check(matches(isDisplayed()));
        }

        @Test
        public void menuAboutUs(){
            authGood();
            onView(withId(R.id.main_menu_image_button)).perform(click());
            onView(withText(R.string.about)).perform(click());
            onView(withId(R.id.container_custom_app_bar_include_on_fragment_about)).check(matches(isDisplayed()));
        }

        @Test
        public void menuMain(){
            menuNews();
            onView(withId(R.id.main_menu_image_button)).perform(click());
            onView(withText(R.string.main)).perform(click());
            onView(withId(R.id.container_custom_app_bar_include_on_fragment_main)).check(matches(isDisplayed()));
        }
}
