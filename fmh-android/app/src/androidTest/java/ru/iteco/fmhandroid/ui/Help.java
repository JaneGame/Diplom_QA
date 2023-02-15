package ru.iteco.fmhandroid.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.AllOf.allOf;
import static ru.iteco.fmhandroid.ui.WaitId.waitId;

import com.google.android.material.textfield.TextInputEditText;

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

    public static void openClaim(){
        authGood();
        onView(withId(R.id.main_menu_image_button)).perform(click());
        onView(withText(R.string.claims)).perform(click());
    }

}
