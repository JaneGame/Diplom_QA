package ru.iteco.fmhandroid.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.hasSibling;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static ru.iteco.fmhandroid.ui.help.Helps.auth;
import static ru.iteco.fmhandroid.ui.help.Helps.checkIfLogin;
import static ru.iteco.fmhandroid.ui.help.Helps.chooseDate;
import static ru.iteco.fmhandroid.ui.help.Helps.chooseDateEnd;
import static ru.iteco.fmhandroid.ui.help.Helps.clickElement;
import static ru.iteco.fmhandroid.ui.help.Helps.clickString;
import static ru.iteco.fmhandroid.ui.help.Helps.foundElement;
import static ru.iteco.fmhandroid.ui.help.Helps.scroll;
import static ru.iteco.fmhandroid.ui.help.Helps.waitElement;
import static ru.iteco.fmhandroid.ui.help.Steps.authYes;
import static ru.iteco.fmhandroid.ui.help.Steps.clickPositive;
import static ru.iteco.fmhandroid.ui.help.Steps.newFound;
import static ru.iteco.fmhandroid.ui.help.Steps.newsWait;
import static ru.iteco.fmhandroid.ui.help.WaitId.waitId;
import static ru.iteco.fmhandroid.ui.help.WorkMenu.openNews;
import static ru.iteco.fmhandroid.ui.help.WorkNews.clickOk;
import static ru.iteco.fmhandroid.ui.help.WorkNews.createNews;
import static ru.iteco.fmhandroid.ui.help.WorkNews.deleteNew;
import static ru.iteco.fmhandroid.ui.help.WorkNews.filterDate;
import static ru.iteco.fmhandroid.ui.help.WorkNews.filterNewsByCategory;
import static ru.iteco.fmhandroid.ui.help.WorkNews.noNew;

import android.widget.DatePicker;

import androidx.test.espresso.PerformException;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
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
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.R;

@LargeTest
//@RunWith(AllureAndroidJUnit4.class)
@Epic("Новости")
@DisplayName("Действия с новостями")
public class NewsTest extends BaseTest {


    @Before

    public void loginIfrequired() {
        Allure.step("Наличие авторизации и вход в систему при необходимости");
        if (!checkIfLogin()) {
            authYes();
        }
        openNews();
    }

    @Test
    @DisplayName("Создание новости")
    public void createNewsTest(){
        LocalDateTime now = LocalDateTime.now();
        String text = "Test" + now;
        String found = "Объявление";
        createNews(text, now, found);
        Allure.step("Поиск созданной новости");
        newsWait();
        newFound(found+text);
    }


    @Test
    @DisplayName("Фильтр по дате")
    public void filterNewsByDate() {
        LocalDateTime now = LocalDateTime.now();
        String text = "Test" + now;
        createNews(text, now, "Объявление");
        Allure.step("Фильтрация по дате");
        filterDate(now);
        Allure.step("Поиск созданной новости");
        newsWait();
        newFound("Объявление"+text);
    }

    @Test
    @DisplayName("Фильтр по категории 'Обявление'")
    public void filterNewsByCategoryAd() {
        LocalDateTime now = LocalDateTime.now();
        String text = "Test" + now;
        String found = "Объявление";
        createNews(text, now, found);
        filterNewsByCategory(found, text);
    }

    @Test
    @DisplayName("Фильтр по категории 'День рождения'")
    public void filterNewsByCategoryHappy() {
    LocalDateTime now = LocalDateTime.now();
    String text = "Test" + now;
    String found = "День рождения";
    createNews(text, now, found);
    filterNewsByCategory(found, text);
}

    @Test
    @DisplayName("Фильтр по категории 'Зарплата'")
    public void filterNewsByCategoryMoney() {
        LocalDateTime now = LocalDateTime.now();
        String text = "Test" + now;
        String found = "Зарплата";
        createNews(text, now, found);
        filterNewsByCategory(found, text);
    }

    @Test
    @DisplayName("Фильтр по категории 'Профсоюз'")
    public void filterNewsByCategoryUnion() {
        LocalDateTime now = LocalDateTime.now();
        String text = "Test" + now;
        String found = "Профсоюз";
        createNews(text, now, found);
        filterNewsByCategory(found, text);
    }


    @Test
    @DisplayName("Фильтр по категории 'Праздник'")
    public void filterNewsByCategoryHoliday() {
        LocalDateTime now = LocalDateTime.now();
        String text = "Test" + now;
        String found = "Праздник";
        createNews(text, now, found);
        filterNewsByCategory(found, text);
    }

    @Test
    @DisplayName("Удаление новости")
    public void deleteNews(){
        LocalDateTime now = LocalDateTime.now();
        String text = "Test" + now;
        String found = "Объявление";
        createNews(text, now, found);
        Allure.step("Поиск новости");
        newsWait();
        newFound((found+text));
        Allure.step("Удаление новости");
        deleteNew((found+text));
        clickPositive();
        Allure.step("Новость не ищется");
        noNew((found+text));
    }
}
