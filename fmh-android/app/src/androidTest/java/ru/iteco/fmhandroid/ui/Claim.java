package ru.iteco.fmhandroid.ui;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static ru.iteco.fmhandroid.ui.Help.Helps.auth;
import static ru.iteco.fmhandroid.ui.Help.Helps.checkIfLogin;
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
import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.Step;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.AppActivity;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
@Epic("Страница с заявками")
@DisplayName("Страница с заявками")
public class Claim {

    @Rule
    public RuleChain chain = RuleChain.outerRule(new ActivityTestRule<>(AppActivity.class))
           .around(new ScreenshotRule(ScreenshotRule.Mode.FAILURE, "ss_end"));

    @Before
    public void loginIfrequired() {
        Allure.step("Наличие авторизации и вход в систему при необходимости");
        if (!checkIfLogin()) {
            auth("login2", "password2");
        }
        openClaim();
    }

    @Test
    @DisplayName("Создание заявки с исполнителем")
    @Description("Создание заявки без указания исполнителя приводит к созданию заявки в в статусе 'В работе'")
    public void executor() throws InterruptedException {
        LocalDateTime now = LocalDateTime.now();
        String text = "Test" + now;
        createClaimBefore(text);
        onView(withId(R.id.executor_drop_menu_auto_complete_text_view)).perform(click());
        workPopup("Ivanov Ivan Ivanovich");
        createClaimAfter(now);
        foundClaim(text);
//        onView(isRoot()).perform(waitId(R.id.status_icon_image_view, 10000)).check(matches(isDisplayed()));
        Thread.sleep(2000);
        Allure.step("Заявка создалась в нужном статусе");
        onView(withText(R.string.in_progress)).check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Создание заявки без исполнителя")
    @Description("Создание заявки без указания исполнителя приводит к созданию заявки в открытом статусе")
    public void noExecutor() throws InterruptedException {
        LocalDateTime now = LocalDateTime.now();
        String text = "Test" + now;
        createClaimBefore(text);
        createClaimAfter(now);
        foundClaim(text);
        Thread.sleep(2000);
        Allure.step("Заявка создалась в нужном статусе");
        onView(withText(R.string.open)).check(matches(isDisplayed()));
    }
    @Test
    @DisplayName("Фильтр заявок")
    @Description("Фильтрация заявок по одному из признаков")
    public void filterClaim(){
        LocalDateTime now = LocalDateTime.now();
        String text = "Test" + now;
        createClaimBefore(text);
        createClaimAfter(now);
        Allure.step("Фильтрация заявок");
        onView(withId(R.id.filters_material_button)).perform(click());
        onView(withId(R.id.item_filter_in_progress)).perform(click());
        onView(withId(R.id.claim_list_filter_ok_material_button)).perform(click());
        foundClaim(text);
    }

    @Test
    @DisplayName("Перевод заявки в статус закрытой")
    @Description("Закрытие заявки")
    public void openToClose() throws InterruptedException {
        LocalDateTime now = LocalDateTime.now();
        String text = "Test" + now;
        createClaimBefore(text);
        createClaimAfter(now);
        foundClaim(text);
        Allure.step("Отмена заявки");
        onView(withId(R.id.status_processing_image_button)).perform(click());
        workPopup("Cancel");
        Thread.sleep(2000);
        Allure.step("Заявка отменена");
        onView(withText(R.string.status_claim_canceled)).check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Взятие заявки в работу")
    @Description("Перевод заявки из статуса 'Открыто' в статус 'В работе'")
    public void openToWork() throws InterruptedException {
        LocalDateTime now = LocalDateTime.now();
        String text = "Test" + now;
        createClaimBefore(text);
        createClaimAfter(now);
        foundClaim(text);
        Allure.step("Перевод заявки в статус 'В работе'");
        onView(withId(R.id.status_processing_image_button)).perform(click());
        workPopup("take to work");
        Thread.sleep(2000);
        Allure.step("Заявка переведена в необходимый статус");
        onView(withText(R.string.in_progress)).check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Изменение заявки")
    @Description("Изменение данных в заявке")
    public void editClaim() throws InterruptedException {
        LocalDateTime now = LocalDateTime.now();
        String text = "Test" + now;
        createClaimBefore(text);
        createClaimAfter(now);
        foundClaim(text);
        Allure.step("Внесение изменений в заявку");
        onView(withId(R.id.edit_processing_image_button)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.executor_drop_menu_auto_complete_text_view)).perform(click());
        workPopup("Ivanov Ivan Ivanovich");
        onView(withId(R.id.executor_drop_menu_auto_complete_text_view)).perform(closeSoftKeyboard());
        onView(withId(R.id.save_button)).perform(click());
        Thread.sleep(2000);
        Allure.step("Заявка переведена в необходимый статус");
        onView(withText(R.string.in_progress)).check(matches(isDisplayed()));
    }
}
