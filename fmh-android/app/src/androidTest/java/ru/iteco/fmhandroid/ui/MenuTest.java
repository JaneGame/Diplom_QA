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
            foundElement(R.id.claim_list_recycler_view);
        }

        @Test
        @DisplayName("Переход к новостям")
        public void menuNews(){
            openNews();
            Allure.step("Новости отображаются");
            foundElement(R.id.all_news_cards_block_constraint_layout);
        }

        @Test
        @DisplayName("Переход к информации о хосписе")
        public void menuAboutUs(){
            authGood();
            Allure.step("Переход к информации о хосписе");
            clickElement(R.id.main_menu_image_button);
            clickString(R.string.about);
            Allure.step("Информация о хосписе отображается");
            waitElement(R.id.container_custom_app_bar_include_on_fragment_about);
        }

        @Test
        @DisplayName("Переход на главную страницу через меню")
        public void menuMain(){
            menuNews();
            Allure.step("Переход на главную страницу");
            clickElement(R.id.main_menu_image_button);
            clickString(R.string.main);
            Allure.step("Главная страница отображается");
            waitElement(R.id.container_custom_app_bar_include_on_fragment_main);
        }
}
