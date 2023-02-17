package ru.iteco.fmhandroid.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.AllOf.allOf;
import static ru.iteco.fmhandroid.ui.WaitId.waitId;

import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.matcher.RootMatchers;

import com.google.android.material.textfield.TextInputEditText;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.time.LocalDateTime;

import ru.iteco.fmhandroid.R;

public class Help {
    public static void auth(String login, String password){
        onView(isRoot()).perform(waitId(R.id.login_text_input_layout, 10000));
        onView(allOf(
                withClassName(containsString(TextInputEditText.class.getSimpleName())),
                isDescendantOfA(withId(R.id.login_text_input_layout))
        )).perform(typeText(login));
        onView(allOf(
                withClassName(containsString(TextInputEditText.class.getSimpleName())),
                isDescendantOfA(withId(R.id.password_text_input_layout))
        )).perform(typeText(password), closeSoftKeyboard());
        onView(withText(R.string.sign_in)).perform(click());
    }

    static boolean checkIfLogin() {
        boolean isLogin = true;
        try {
            onView(isRoot()).perform(waitId(R.id.main_swipe_refresh, 15000));
        } catch (RuntimeException ex) {
            isLogin = false;
        }
        return isLogin;
    }

    public static void authGood(){
        onView(isRoot()).perform(waitId(R.id.main_swipe_refresh, 15000)).check(matches(isDisplayed()));
    }

    public static void createClaimBefore(){
        onView(withId(R.id.add_new_claim_material_button)).perform(click());
        LocalDateTime now = LocalDateTime.now();
        final String text = "Test" + now;
        onView(withId(R.id.title_edit_text)).perform(typeText(text));
        onView(withId(R.id.executor_drop_menu_auto_complete_text_view)).perform(click());
//        createClaimBefore();
        onView(withText("Ivanov Ivan Ivanovich"))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(click());
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
    }

    public static void openClaim(){
        authGood();
        onView(withId(R.id.main_menu_image_button)).perform(click());
        onView(withText(R.string.claims)).perform(click());
    }

    public static Matcher<View> withIndex(final Matcher<View> matcher, final int index) {
        return new TypeSafeMatcher<View>() {
            int currentIndex = 0;

            @Override
            public void describeTo(Description description) {
                description.appendText("with index: ");
                description.appendValue(index);
                matcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                return matcher.matches(view) && currentIndex++ == index;
            }
        };
    }

}
