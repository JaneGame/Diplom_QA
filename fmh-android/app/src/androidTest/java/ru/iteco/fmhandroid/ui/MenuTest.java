package ru.iteco.fmhandroid.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


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
import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.R;

import static ru.iteco.fmhandroid.ui.help.Helps.auth;
import static ru.iteco.fmhandroid.ui.help.Helps.authGood;
import static ru.iteco.fmhandroid.ui.help.Helps.checkIfLogin;
import static ru.iteco.fmhandroid.ui.help.Helps.clickElement;
import static ru.iteco.fmhandroid.ui.help.Helps.clickString;
import static ru.iteco.fmhandroid.ui.help.Helps.foundElement;
import static ru.iteco.fmhandroid.ui.help.Helps.waitElement;
import static ru.iteco.fmhandroid.ui.help.Steps.aboutWait;
import static ru.iteco.fmhandroid.ui.help.Steps.allClaimsGood;
import static ru.iteco.fmhandroid.ui.help.Steps.allNewsGood;
import static ru.iteco.fmhandroid.ui.help.Steps.mainWait;
import static ru.iteco.fmhandroid.ui.help.WorkMenu.Main;
import static ru.iteco.fmhandroid.ui.help.WorkMenu.openAbout;
import static ru.iteco.fmhandroid.ui.help.WorkMenu.openClaim;
import static ru.iteco.fmhandroid.ui.help.WorkMenu.openNews;


@LargeTest
//@RunWith(AllureAndroidJUnit4.class)
@Epic("Меню")
@DisplayName("Переходы через меню")
public class MenuTest extends BaseTest {

        @Before
        public void loginIfrequired() {
            Allure.step("Наличие авторизации и вход в систему при необходимости");
            if (!checkIfLogin()) {
                auth("login2", "password2");

        }}



        @Test
        @DisplayName("Переход к заявкам")
            public void menuClaims(){
            openClaim();
            Allure.step("Заявки отображаются");
            allClaimsGood();
        }

        @Test
        @DisplayName("Переход к новостям")
        public void menuNews(){
            openNews();
            Allure.step("Новости отображаются");
            allNewsGood();
        }

        @Test
        @DisplayName("Переход к информации о хосписе")
        public void menuAboutUs(){
            openAbout();
            Allure.step("Информация о хосписе отображается");
            aboutWait();
        }

        @Test
        @DisplayName("Переход на главную страницу через меню")
        public void menuMain(){
            menuNews();
            Main();
            Allure.step("Главная страница отображается");
            mainWait();
        }
}
