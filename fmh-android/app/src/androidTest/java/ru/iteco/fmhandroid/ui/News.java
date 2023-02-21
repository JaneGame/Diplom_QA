package ru.iteco.fmhandroid.ui;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.hasSibling;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.AllOf.allOf;
import static ru.iteco.fmhandroid.ui.Help.Helps.auth;
import static ru.iteco.fmhandroid.ui.Help.Helps.checkIfLogin;
import static ru.iteco.fmhandroid.ui.Help.Helps.workPopup;
import static ru.iteco.fmhandroid.ui.Help.WaitId.waitId;
import static ru.iteco.fmhandroid.ui.Help.WorkMenu.openClaim;
import static ru.iteco.fmhandroid.ui.Help.WorkMenu.openNews;
import static ru.iteco.fmhandroid.ui.Help.WorkNews.createNews;
import static ru.iteco.fmhandroid.ui.Help.WorkNews.filterNewsByCategory;

import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.PerformException;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.google.android.material.textfield.TextInputEditText;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import io.qameta.allure.android.rules.ScreenshotRule;
import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Step;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.R;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class News {

    @Rule
    public RuleChain chain = RuleChain.outerRule(new ActivityTestRule<>(AppActivity.class))
            .around(new ScreenshotRule(ScreenshotRule.Mode.FAILURE, "ss_end"));

    @Before
    @Step("Авторизация при её отсутствиии переход к новостям")
    public void loginIfrequired() {
        if (!checkIfLogin()) {
            auth("login2", "password2");
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
        onView(isRoot()).perform(waitId(R.id.news_list_recycler_view, 10000));
        onView(ViewMatchers.withId(R.id.news_list_recycler_view))
                .perform(RecyclerViewActions.scrollTo(hasDescendant(withText(found+text))))
                .check(matches(isDisplayed()));
    }


    @Test
    @DisplayName("Фильтр по дате")
    public void filterNewsByDate() {
        LocalDateTime now = LocalDateTime.now();
        String text = "Test" + now;
        createNews(text, now, "Объявление");
        onView(withId(R.id.filter_news_material_button)).perform(click());

        onView(withId(R.id.news_item_publish_date_start_text_input_edit_text)).perform(click());

        onView(isAssignableFrom(DatePicker.class))
                .perform(PickerActions.setDate(
                        now.getYear(),
                        now.getMonthValue(),
                        now.getDayOfMonth() - 1)
                );

        onView(withText(android.R.string.ok)).perform(click());

        onView(withId(R.id.news_item_publish_date_end_text_input_edit_text)).perform(click());

        onView(isAssignableFrom(DatePicker.class))
                .perform(PickerActions.setDate(
                        now.getYear(),
                        now.getMonthValue(),
                        now.getDayOfMonth())
                );

        onView(withText(android.R.string.ok)).perform(click());

        onView(withId(R.id.filter_button)).perform(click());

        onView(isRoot()).perform(waitId(R.id.news_list_recycler_view, 10000));
        onView(ViewMatchers.withId(R.id.news_list_recycler_view))
                .perform(RecyclerViewActions.scrollTo(hasDescendant(withText("Объявление"+text))))
                .check(matches(isDisplayed()));
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
        onView(isRoot()).perform(waitId(R.id.news_list_recycler_view, 10000));
        onView(ViewMatchers.withId(R.id.news_list_recycler_view))
                .perform(RecyclerViewActions.scrollTo(hasDescendant(withText(found+text))));
        onView(allOf(hasSibling(withText(found+text)), withId(R.id.delete_news_item_image_view))).perform(click());
        onView(withText(R.string.fragment_positive_button)).perform(click());
        try{onView(ViewMatchers.withId(R.id.news_list_recycler_view))
                .perform(RecyclerViewActions.scrollTo(hasDescendant(withText(found+text))));
        }catch (PerformException ex){
            onView(withId((R.id.news_list_recycler_view)));
        };

    }
}
