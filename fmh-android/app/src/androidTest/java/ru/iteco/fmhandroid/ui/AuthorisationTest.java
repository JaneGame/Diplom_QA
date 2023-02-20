package ru.iteco.fmhandroid.ui;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static ru.iteco.fmhandroid.ui.Help.Helps.auth;
import static ru.iteco.fmhandroid.ui.Help.Helps.authGood;
import static ru.iteco.fmhandroid.ui.Help.Helps.checkIfLogin;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.Step;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.R;

import static org.hamcrest.core.AllOf.allOf;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class AuthorisationTest {

    @Rule
    public ActivityTestRule<AppActivity> mActivityTestRule = new ActivityTestRule<>(AppActivity.class);

    @Before
    @Step("Выход при наличии авторизации")
    public void loginIfNoRequired() {
        if (checkIfLogin()) {
            onView(withId(R.id.authorization_image_button)).check(matches(isDisplayed()))
                    .perform(click());
            onView(withText(R.string.log_out)).perform(click());
    }}

    @Test
    @DisplayName("Удачная авторизация")
    @Description("Вход с указанием корректных логина и пароля")
    public void authorisationTrue() {
        auth("login2", "password2");
        authGood();
    }


    @Test
    @DisplayName("Неудачная авторизация")
    @Description("Вход с ошибочными данными")
    public void authorisationFalse() {
        auth("login2", "Password2");
        onView(withText(R.string.authorization)).check(matches(isDisplayed()));
    }

    }



