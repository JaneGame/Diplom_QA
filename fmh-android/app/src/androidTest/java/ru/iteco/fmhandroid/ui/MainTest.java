package ru.iteco.fmhandroid.ui;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static ru.iteco.fmhandroid.ui.help.Helps.auth;
import static ru.iteco.fmhandroid.ui.help.Helps.authGood;
import static ru.iteco.fmhandroid.ui.help.Helps.changeIndex;
import static ru.iteco.fmhandroid.ui.help.Helps.checkIfLogin;
import static ru.iteco.fmhandroid.ui.help.Helps.clickElement;
import static ru.iteco.fmhandroid.ui.help.Helps.clickString;
import static ru.iteco.fmhandroid.ui.help.Helps.foundElement;
import static ru.iteco.fmhandroid.ui.help.Helps.waitElement;
import static ru.iteco.fmhandroid.ui.help.Helps.withIndex;
import static ru.iteco.fmhandroid.ui.help.Helps.workPopup;
import static ru.iteco.fmhandroid.ui.help.Steps.allClaimsClick;
import static ru.iteco.fmhandroid.ui.help.Steps.allClaimsGood;
import static ru.iteco.fmhandroid.ui.help.Steps.allNewsClick;
import static ru.iteco.fmhandroid.ui.help.Steps.allNewsGood;
import static ru.iteco.fmhandroid.ui.help.Steps.authButton;
import static ru.iteco.fmhandroid.ui.help.Steps.authYes;
import static ru.iteco.fmhandroid.ui.help.Steps.changeNew;
import static ru.iteco.fmhandroid.ui.help.Steps.chooseClaim;
import static ru.iteco.fmhandroid.ui.help.Steps.logOut;
import static ru.iteco.fmhandroid.ui.help.Steps.missionButton;
import static ru.iteco.fmhandroid.ui.help.Steps.missionWait;
import static ru.iteco.fmhandroid.ui.help.Steps.startPage;
import static ru.iteco.fmhandroid.ui.help.Steps.waitClaim;
import static ru.iteco.fmhandroid.ui.help.Steps.waitNew;
import static ru.iteco.fmhandroid.ui.help.WaitId.waitId;
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
import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.R;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
@Epic("Главная страница")
@DisplayName("Действия на главной странице")
public class MainTest extends BaseTest {

    @Before
    public void loginIfrequired() {
        Allure.step("Наличие авторизации и вход в систему при необходимости");
        if (!checkIfLogin()) {
            authYes();
            authGood();
        }
    }

    @Test
    @DisplayName("Просмотр всех новостей")
    public void allNews(){
        Allure.step("Нажатие на 'Все новости'");
        allNewsClick();
        Allure.step("Переход ко всем новостям произошел удачно");
        allNewsGood();
    }

    @Test
    @DisplayName("Просмотр заявки")
    public void claimView(){
        Allure.step("Нажатие на заявку");
        chooseClaim();
        Allure.step("Происходит показ заявки");
        waitClaim();
    }

    @Test
    @DisplayName("Просмотр всех заявок")
    public void allClaim(){
        Allure.step("Нажатие на 'заявки'");
        allClaimsClick();
        Allure.step("Переход ко всем заявкам произошел удачно");
        allClaimsGood();
    }


    @Test
    @DisplayName("Создание заявки с главной страницы")
    public void createClaimMain(){
        Allure.step("Переход к форме создания заявки");
        LocalDateTime now = LocalDateTime.now();
        String text = "Test" + now;
        createClaimBefore(text);
        createClaimAfter(now);
        openClaim();
        foundClaim(text);
    }

    @Test
    @DisplayName("Просмотр новости с главной страницы")
    public void newView() {
        Allure.step("Выбор новости");
        changeNew();
        Allure.step("Просмотр новости");
        waitNew();
    }

    @Test
    @DisplayName("Просмотр цитат")
    public void quote(){
        Allure.step("Нажатие на изображение бабочки");
        missionButton();
        Allure.step("Переход к цитатам");
        missionWait();
    }

    @Test
    @DisplayName("Выход из аккаунта")
    public void exit(){
        Allure.step("Выход из системы");
        authButton();
        logOut();
        Allure.step("Выход произведен успешно");
        startPage();
    }
}
