package ru.iteco.fmhandroid.ui;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static ru.iteco.fmhandroid.ui.help.Helps.auth;
import static ru.iteco.fmhandroid.ui.help.Helps.checkIfLogin;
import static ru.iteco.fmhandroid.ui.help.Helps.clickElement;
import static ru.iteco.fmhandroid.ui.help.Helps.clickString;
import static ru.iteco.fmhandroid.ui.help.Helps.clickStringWait;
import static ru.iteco.fmhandroid.ui.help.Helps.closeKeyboard;
import static ru.iteco.fmhandroid.ui.help.Helps.foundString;
import static ru.iteco.fmhandroid.ui.help.Helps.waitElementAndClick;
import static ru.iteco.fmhandroid.ui.help.Helps.workPopup;
import static ru.iteco.fmhandroid.ui.help.WorkClaim.createClaimAfter;
import static ru.iteco.fmhandroid.ui.help.WorkClaim.createClaimBefore;
import static ru.iteco.fmhandroid.ui.help.WorkClaim.foundClaim;
import static ru.iteco.fmhandroid.ui.help.WorkMenu.openClaim;

import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDateTime;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;

import io.qameta.allure.kotlin.Allure;
import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.R;

@LargeTest
//@RunWith(AllureAndroidJUnit4.class)
@Epic("Страница с заявками")
@DisplayName("Страница с заявками")
public class ClaimTest extends BaseTest {

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
    public void executor() {
        LocalDateTime now = LocalDateTime.now();
        String text = "Test" + now;
        createClaimBefore(text);
        clickElement(R.id.executor_drop_menu_auto_complete_text_view);
        workPopup("Ivanov Ivan Ivanovich");
        createClaimAfter(now);
        foundClaim(text);
        Allure.step("Заявка создалась в нужном статусе");
        clickStringWait(R.string.in_progress);
    }

    @Test
    @DisplayName("Создание заявки без исполнителя")
    @Description("Создание заявки без указания исполнителя приводит к созданию заявки в открытом статусе")
    public void noExecutor() {
        LocalDateTime now = LocalDateTime.now();
        String text = "Test" + now;
        createClaimBefore(text);
        createClaimAfter(now);
        foundClaim(text);
        Allure.step("Заявка создалась в нужном статусе");
        clickStringWait(R.string.open);
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
        clickElement(R.id.filters_material_button);
        clickElement(R.id.item_filter_in_progress);
        clickElement(R.id.claim_list_filter_ok_material_button);
        foundClaim(text);
    }

    @Test
    @DisplayName("Перевод заявки в статус закрытой")
    @Description("Закрытие заявки")
    public void openToClose() {
        LocalDateTime now = LocalDateTime.now();
        String text = "Test" + now;
        createClaimBefore(text);
        createClaimAfter(now);
        foundClaim(text);
        Allure.step("Отмена заявки");
        clickElement(R.id.status_processing_image_button);
        workPopup("Cancel");
        Allure.step("Заявка отменена");
        clickStringWait(R.string.status_claim_canceled);
    }

    @Test
    @DisplayName("Взятие заявки в работу")
    @Description("Перевод заявки из статуса 'Открыто' в статус 'В работе'")
    public void openToWork() {
        LocalDateTime now = LocalDateTime.now();
        String text = "Test" + now;
        createClaimBefore(text);
        createClaimAfter(now);
        foundClaim(text);
        Allure.step("Перевод заявки в статус 'В работе'");
        clickElement(R.id.status_processing_image_button);
        workPopup("take to work");
        Allure.step("Заявка переведена в необходимый статус");
        clickStringWait(R.string.in_progress);
    }

    @Test
    @DisplayName("Изменение заявки")
    @Description("Изменение данных в заявке")
    public void editClaim() {
        LocalDateTime now = LocalDateTime.now();
        String text = "Test" + now;
        createClaimBefore(text);
        createClaimAfter(now);
        foundClaim(text);
        Allure.step("Внесение изменений в заявку");
        clickElement(R.id.edit_processing_image_button);
        waitElementAndClick(R.id.executor_drop_menu_auto_complete_text_view);
        workPopup("Ivanov Ivan Ivanovich");
        closeKeyboard(R.id.executor_drop_menu_auto_complete_text_view);
        clickElement(R.id.save_button);
        Allure.step("Заявка переведена в необходимый статус");
        clickStringWait(R.string.in_progress);
    }
}
