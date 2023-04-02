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
import static ru.iteco.fmhandroid.ui.help.Steps.authYes;
import static ru.iteco.fmhandroid.ui.help.Steps.claimEdit;
import static ru.iteco.fmhandroid.ui.help.Steps.claimInCanceled;
import static ru.iteco.fmhandroid.ui.help.Steps.claimInOpen;
import static ru.iteco.fmhandroid.ui.help.Steps.claimInProgress;
import static ru.iteco.fmhandroid.ui.help.Steps.clickFilter;
import static ru.iteco.fmhandroid.ui.help.Steps.clickSave;
import static ru.iteco.fmhandroid.ui.help.Steps.closeKeybordInClaim;
import static ru.iteco.fmhandroid.ui.help.Steps.executorChange;
import static ru.iteco.fmhandroid.ui.help.Steps.filterInProgress;
import static ru.iteco.fmhandroid.ui.help.Steps.filterOk;
import static ru.iteco.fmhandroid.ui.help.Steps.statusButton;
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
            authYes();
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
        executorChange();
        workPopup("Ivanov Ivan Ivanovich");
        createClaimAfter(now);
        foundClaim(text);
        Allure.step("Заявка создалась в нужном статусе");
        claimInProgress();
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
        claimInOpen();
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
        clickFilter();
        filterInProgress();
        filterOk();
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
        statusButton();
        workPopup("Cancel");
        Allure.step("Заявка отменена");
        claimInCanceled();
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
        statusButton();
        workPopup("take to work");
        Allure.step("Заявка переведена в необходимый статус");
        claimInProgress();
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
        claimEdit();
        executorChange();
        workPopup("Ivanov Ivan Ivanovich");
        closeKeybordInClaim();
        clickSave();
        Allure.step("Заявка переведена в необходимый статус");
        claimInProgress();
    }
}
