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
    @Step("Авторизация при её отсутствии")
    public void loginIfrequired() {
        if (!checkIfLogin()) {
            auth("login2", "password2");
            authGood();
        }
    }

    @Test
    @DisplayName("Просмотр всех новостей")
    public void allNews(){
        onView(withText(R.string.all_news)).perform(click());
        onView(withId(R.id.all_news_cards_block_constraint_layout)).check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Просмотр заявки")
    public void claimView(){
        onView(withIndex(withId(R.id.claim_list_card), 0)).perform(click());
        onView(isRoot()).perform(waitId(R.id.container_custom_app_bar_include_on_fragment_open_claim, 5000)).check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Просмотр всех заявок")
    public void allClaim(){
        onView(withText(R.string.all_claims)).perform(click());
        onView(isRoot()).perform(waitId(R.id.container_custom_app_bar_include_on_fragment_list_claim, 10000)).check(matches(isDisplayed()));
    }


    @Test
    @DisplayName("Создание заявки с главной страницы")
    public void createClaimMain(){
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
        onView(withIndex(withId(R.id.news_item_material_card_view), 0)).perform(click());
        onView(isRoot()).perform(waitId(R.id.news_item_description_text_view, 5000)).check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Просмотр цитат")
    public void quote(){
        onView(withId(R.id.our_mission_image_button)).perform(click());
        onView(isRoot()).perform(waitId(R.id.our_mission_title_text_view, 5000)).check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Выход из аккаунта")
    public void exit(){
        onView(withId(R.id.authorization_image_button)).perform(click());
        workPopup("Log out");
        onView(isRoot()).perform(waitId(R.id.login_text_input_layout, 5000)).check(matches(isDisplayed()));
    }
}
