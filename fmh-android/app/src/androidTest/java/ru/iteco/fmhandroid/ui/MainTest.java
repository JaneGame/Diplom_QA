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
            auth("login2", "password2");
            authGood();
        }
    }

    @Test
    @DisplayName("Просмотр всех новостей")
    public void allNews(){
        Allure.step("Нажатие на 'Все новости'");
        clickString(R.string.all_news);
        Allure.step("Переход ко всем новостям произошел удачно");
        foundElement(R.id.all_news_cards_block_constraint_layout);
    }

    @Test
    @DisplayName("Просмотр заявки")
    public void claimView(){
        Allure.step("Нажатие на заявку");
        changeIndex(R.id.claim_list_card);
        Allure.step("Происходит показ заявки");
        waitElement(R.id.container_custom_app_bar_include_on_fragment_open_claim);
    }

    @Test
    @DisplayName("Просмотр всех заявок")
    public void allClaim(){
        Allure.step("Нажатие на 'заявки'");
        clickString(R.string.all_claims);
        Allure.step("Переход ко всем заявкам произошел удачно");
        waitElement(R.id.container_custom_app_bar_include_on_fragment_list_claim);
    }


    @Test
    @DisplayName("Создание заявки с главной страницы")
    public void createClaimMain(){
        Allure.step("Переход к форме создания заявки");
        waitElement(R.id.add_new_claim_material_button);
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
        changeIndex(R.id.news_item_material_card_view);
        Allure.step("Просмотр новости");
        waitElement(R.id.news_item_description_text_view);
    }

    @Test
    @DisplayName("Просмотр цитат")
    public void quote(){
        Allure.step("Нажатие на изображение бабочки");
        clickElement(R.id.our_mission_image_button);
        Allure.step("Переход к цитатам");
        waitElement(R.id.our_mission_title_text_view);
    }

    @Test
    @DisplayName("Выход из аккаунта")
    public void exit(){
        Allure.step("Выход из системы");
        clickElement(R.id.authorization_image_button);
        workPopup("Log out");
        Allure.step("Выход произведен успешно");
        waitElement(R.id.login_text_input_layout);
    }
}
