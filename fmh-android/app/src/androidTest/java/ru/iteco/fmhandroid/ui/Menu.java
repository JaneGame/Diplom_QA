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
public class Menu {


        @Rule
        public RuleChain chain = RuleChain.outerRule(new ActivityTestRule<>(AppActivity.class))
                .around(new ScreenshotRule(ScreenshotRule.Mode.FAILURE, "ss_end"));

        @Before
        @Step("Авторизация при её отсутствии")
        public void loginIfrequired() {
            if (!checkIfLogin()) {
                auth("login2", "password2");
        }}



        @Test
        @DisplayName("Переход к заявкам")
            public void menuClaims(){
            openClaim();
            onView(withId(R.id.claim_list_recycler_view)).check(matches(isDisplayed()));
        }

        @Test
        @DisplayName("Переход к новостям")
        public void menuNews(){
            openNews();
            onView(withId(R.id.all_news_cards_block_constraint_layout)).check(matches(isDisplayed()));
        }

        @Test
        @DisplayName("Переход к информации о хосписе")
        public void menuAboutUs(){
            authGood();
            onView(withId(R.id.main_menu_image_button)).perform(click());
            onView(withText(R.string.about)).perform(click());
            onView(withId(R.id.container_custom_app_bar_include_on_fragment_about)).check(matches(isDisplayed()));
        }

        @Test
        @DisplayName("Переход на главную страницу")
        public void menuMain(){
            menuNews();
            onView(withId(R.id.main_menu_image_button)).perform(click());
            onView(withText(R.string.main)).perform(click());
            onView(withId(R.id.container_custom_app_bar_include_on_fragment_main)).check(matches(isDisplayed()));
        }
}
