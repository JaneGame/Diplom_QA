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
import io.qameta.allure.kotlin.Step;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.R;

import static ru.iteco.fmhandroid.ui.Help.Helps.auth;
import static ru.iteco.fmhandroid.ui.Help.Helps.authGood;
import static ru.iteco.fmhandroid.ui.Help.Helps.checkIfLogin;
import static ru.iteco.fmhandroid.ui.Help.WorkMenu.openClaim;
import static ru.iteco.fmhandroid.ui.Help.WorkMenu.openNews;


@LargeTest
@RunWith(AllureAndroidJUnit4.class)
@Epic("Меню")
@DisplayName("Переходы через меню")
public class Menu {


        @Rule
        public RuleChain chain = RuleChain.outerRule(new ActivityTestRule<>(AppActivity.class))
                .around(new ScreenshotRule(ScreenshotRule.Mode.FAILURE, "ss_end"));

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
            onView(withId(R.id.claim_list_recycler_view)).check(matches(isDisplayed()));
        }

        @Test
        @DisplayName("Переход к новостям")
        public void menuNews(){
            openNews();
            Allure.step("Новости отображаются");
            onView(withId(R.id.all_news_cards_block_constraint_layout)).check(matches(isDisplayed()));
        }

        @Test
        @DisplayName("Переход к информации о хосписе")
        public void menuAboutUs(){
            authGood();
            Allure.step("Переход к информации о хосписе");
            onView(withId(R.id.main_menu_image_button)).perform(click());
            onView(withText(R.string.about)).perform(click());
            Allure.step("Информация о хосписе отображается");
            onView(withId(R.id.container_custom_app_bar_include_on_fragment_about)).check(matches(isDisplayed()));
        }

        @Test
        @DisplayName("Переход на главную страницу через меню")
        public void menuMain(){
            menuNews();
            Allure.step("Переход на главную страницу");
            onView(withId(R.id.main_menu_image_button)).perform(click());
            onView(withText(R.string.main)).perform(click());
            Allure.step("Главная страница отображается");
            onView(withId(R.id.container_custom_app_bar_include_on_fragment_main)).check(matches(isDisplayed()));
        }
}
