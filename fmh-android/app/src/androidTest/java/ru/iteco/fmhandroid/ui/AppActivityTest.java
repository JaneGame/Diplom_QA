package ru.iteco.fmhandroid.ui;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.doubleClick;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


import static ru.iteco.fmhandroid.ui.waitID.waitId;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.waitID;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDateTime;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class AppActivityTest {

    @Rule
    public ActivityTestRule<AppActivity> mActivityTestRule = new ActivityTestRule<>(AppActivity.class);

    @Before
//    static void startActivity() {
//        onView(isRoot()).perform(waitId(R.id.login_text_input_layout, 10000));
//        //новый степ который вводит текст login2 в EditText формы логина:
//        onView(allOf(
//                withClassName(containsString(TextInputEditText.class.getSimpleName())),
//                isDescendantOfA(withId(R.id.login_text_input_layout))
//        )).perform(typeText("login2"));
//        onView(allOf(
//                withClassName(containsString(TextInputEditText.class.getSimpleName())),
//                isDescendantOfA(withId(R.id.password_text_input_layout))
//        )).perform(typeText("password2"), closeSoftKeyboard());
//    }

    @Test

    public void appActivityTest() {
        onView(isRoot()).perform(waitId(R.id.login_text_input_layout, 10000));
        //новый степ который вводит текст login2 в EditText формы логина:
        onView(allOf(
                withClassName(containsString(TextInputEditText.class.getSimpleName())),
                isDescendantOfA(withId(R.id.login_text_input_layout))
        )).perform(typeText("login2"));
        onView(allOf(
                withClassName(containsString(TextInputEditText.class.getSimpleName())),
                isDescendantOfA(withId(R.id.password_text_input_layout))
        )).perform(typeText("password2"), closeSoftKeyboard());
        onView(withText(R.string.sign_in)).perform(click());
        onView(withId(R.id.authorization_image_button)).check(matches(isDisplayed()));
    }

    @Test
    public void filterNewsByDate() {
        onView(isRoot()).perform(waitId(R.id.login_text_input_layout, 10000));

        onView(allOf(
                instanceOf(EditText.class),
                isDescendantOfA(withId(R.id.login_text_input_layout))
        )).perform(replaceText("login2"));

        onView(allOf(
                instanceOf(EditText.class),
                isDescendantOfA(withId(R.id.password_text_input_layout))
        )).perform(replaceText("password2"));

        onView(withText(R.string.sign_in)).perform(click());

        onView(isRoot()).perform(waitId(R.id.main_swipe_refresh, 10000));

        onView(withId(R.id.main_menu_image_button)).perform(click());

        onView(withText(R.string.news)).perform(click());

        onView(withId(R.id.filter_news_material_button)).perform(click());

        onView(withId(R.id.news_item_publish_date_start_text_input_edit_text)).perform(click());

        LocalDateTime now = LocalDateTime.now();

        onView(isAssignableFrom(DatePicker.class))
                .perform(PickerActions.setDate(
                        now.getYear(),
                        now.getMonthValue(),
                        //for example choosing 1 day gap from today
                        now.getDayOfMonth() - 1)
                );

        onView(withText(android.R.string.ok)).perform(click());

        onView(withId(R.id.news_item_publish_date_end_text_input_edit_text)).perform(click());

        onView(isAssignableFrom(DatePicker.class))
                .perform(PickerActions.setDate(
                        now.getYear(),
                        now.getMonthValue(),
                        now.getDayOfMonth())
                );

        onView(withText(android.R.string.ok)).perform(click());

        onView(withId(R.id.filter_button)).perform(click());

        onView(isRoot()).perform(waitId(R.id.news_list_recycler_view, 10000));

    }}



