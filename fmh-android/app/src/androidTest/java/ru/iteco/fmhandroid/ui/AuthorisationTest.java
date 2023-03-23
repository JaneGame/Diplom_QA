package ru.iteco.fmhandroid.ui;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static ru.iteco.fmhandroid.ui.help.Helps.auth;
import static ru.iteco.fmhandroid.ui.help.Helps.authGood;
import static ru.iteco.fmhandroid.ui.help.Helps.checkIfLogin;
import static ru.iteco.fmhandroid.ui.help.Helps.clickAndFoundElement;
import static ru.iteco.fmhandroid.ui.help.Helps.clickString;
import static ru.iteco.fmhandroid.ui.help.Helps.foundString;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;

import io.qameta.allure.android.rules.ScreenshotRule;
import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Allure;
import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.R;

import static org.hamcrest.core.AllOf.allOf;

@LargeTest
//@RunWith(AllureAndroidJUnit4.class)
@Epic("Авторизация")
@DisplayName("Авторизация")
public class AuthorisationTest extends BaseTest{


    @Before
    public void loginIfNoRequired() {
        Allure.step("Отсутствие авторизации и выход из системы при необходимости");
        if (checkIfLogin()) {

            clickAndFoundElement(R.id.authorization_image_button);
            clickString(R.string.log_out);
        }}

    @Test
    @DisplayName("Удачная авторизация")
    @Description("Вход с указанием корректных логина и пароля")
    public void authorisationTrue() {
        Allure.step("Авторизация с корректными данными");
        auth("login2", "password2");
        Allure.step("Успешный вход");
        authGood();
    }


    @Test
    @DisplayName("Неудачная авторизация")
    @Description("Вход с ошибочными данными")
    public void authorisationFalse() {
        Allure.step("Авторизация с неверными данными");
        auth("login2", "Password2");
        Allure.step("Авторизация не удалась");
        foundString(R.string.authorization);
    }

    }



