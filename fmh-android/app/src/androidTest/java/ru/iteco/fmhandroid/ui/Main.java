package ru.iteco.fmhandroid.ui;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static ru.iteco.fmhandroid.ui.Help.Helps.auth;
import static ru.iteco.fmhandroid.ui.Help.Helps.authGood;
import static ru.iteco.fmhandroid.ui.Help.Helps.checkIfLogin;
import static ru.iteco.fmhandroid.ui.Help.Helps.withIndex;
import static ru.iteco.fmhandroid.ui.Help.Helps.workPopup;
import static ru.iteco.fmhandroid.ui.Help.WaitId.waitId;
import static ru.iteco.fmhandroid.ui.Help.WorkClaim.createClaimAfter;
import static ru.iteco.fmhandroid.ui.Help.WorkClaim.createClaimBefore;
import static ru.iteco.fmhandroid.ui.Help.WorkClaim.foundClaim;
import static ru.iteco.fmhandroid.ui.Help.WorkMenu.openClaim;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;

import java.time.LocalDateTime;

import io.qameta.allure.android.rules.ScreenshotRule;
import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Allure;
import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.Step;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.R;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
@Epic("Главная страница")
@DisplayName("Действия на главной странице")
public class Main {

    @Rule
    public RuleChain chain = RuleChain.outerRule(new ActivityTestRule<>(AppActivity.class))
            .around(new ScreenshotRule(ScreenshotRule.Mode.FAILURE, "ss_end"));

    @Before
    public void loginIfrequired() {
        Allure.step("Наличие авторизации и вход в систему при необходимости");
        if (!checkIfLogin()) {
            auth("login2", "password2");
            authGood();
        }
    }

    @Test
    @DisplayName("Просмотр всех новостей")
    public void allNews(){
        Allure.step("Нажатие на 'Все новости'");
        onView(withText(R.string.all_news)).perform(click());
        Allure.step("Переход ко всем новостям произошел удачно");
        onView(withId(R.id.all_news_cards_block_constraint_layout)).check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Просмотр заявки")
    public void claimView(){
        Allure.step("Нажатие на заявку");
        onView(withIndex(withId(R.id.claim_list_card), 0)).perform(click());
        Allure.step("Происходит показ заявки");
        onView(isRoot()).perform(waitId(R.id.container_custom_app_bar_include_on_fragment_open_claim, 5000)).check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Просмотр всех заявок")
    public void allClaim(){
        Allure.step("Нажатие на 'заявки'");
        onView(withText(R.string.all_claims)).perform(click());
        Allure.step("Переход ко всем заявкам произошел удачно");
        onView(isRoot()).perform(waitId(R.id.container_custom_app_bar_include_on_fragment_list_claim, 10000)).check(matches(isDisplayed()));
    }


    @Test
    @DisplayName("Создание заявки с главной страницы")
    public void createClaimMain(){
        Allure.step("Переход к форме создания заявки");
        onView(isRoot()).perform(waitId(R.id.add_new_claim_material_button, 15000)).perform(click());
        LocalDateTime now = LocalDateTime.now();
        String text = "Test" + now;
        createClaimBefore(text);
        createClaimAfter(now);
        openClaim();
        foundClaim(text);
    }

    @Test
    @DisplayName("Просмотр новости с главной страницы")
    public void newView(){
        Allure.step("Выбор новости");
        onView(withIndex(withId(R.id.news_item_material_card_view), 0)).perform(click());
        Allure.step("Просмотр новости");
        onView(isRoot()).perform(waitId(R.id.news_item_description_text_view, 5000)).check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Просмотр цитат")
    public void quote(){
        Allure.step("Нажатие на изображение бабочки");
        onView(withId(R.id.our_mission_image_button)).perform(click());
        Allure.step("Переход к цитатам");
        onView(isRoot()).perform(waitId(R.id.our_mission_title_text_view, 5000)).check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Выход из аккаунта")
    public void exit(){
        Allure.step("Выход из системы");
        onView(withId(R.id.authorization_image_button)).perform(click());
        workPopup("Log out");
        Allure.step("Выход произведен успешно");
        onView(isRoot()).perform(waitId(R.id.login_text_input_layout, 5000)).check(matches(isDisplayed()));
    }
}
