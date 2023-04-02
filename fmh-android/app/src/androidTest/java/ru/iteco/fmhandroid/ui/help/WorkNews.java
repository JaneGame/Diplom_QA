package ru.iteco.fmhandroid.ui.help;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.hasSibling;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static ru.iteco.fmhandroid.ui.help.Helps.chooseDate;
import static ru.iteco.fmhandroid.ui.help.Helps.chooseDateEnd;
import static ru.iteco.fmhandroid.ui.help.Helps.clickElement;
import static ru.iteco.fmhandroid.ui.help.Helps.foundElement;
import static ru.iteco.fmhandroid.ui.help.Helps.scroll;
import static ru.iteco.fmhandroid.ui.help.Helps.waitElement;
import static ru.iteco.fmhandroid.ui.help.Helps.workPopup;
import static ru.iteco.fmhandroid.ui.help.WaitId.waitId;

import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.PerformException;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;

import java.time.LocalDateTime;

import io.qameta.allure.kotlin.Allure;
import io.qameta.allure.kotlin.Step;
import ru.iteco.fmhandroid.R;

public class WorkNews {

    @Step("Создание новости")
    public static void createNews(String text, LocalDateTime now, String popup){
        Allure.step("Создание новости");
        onView(withId(R.id.edit_news_material_button)).perform(click());
        onView(withId(R.id.add_news_image_view)).perform(click());
        onView(withId(R.id.news_item_category_text_auto_complete_text_view)).perform(click());
        workPopup(popup);
        onView(withId(R.id.news_item_title_text_input_edit_text)).perform(typeText(text));
        onView(withId(R.id.news_item_publish_date_text_input_edit_text)).perform(click());
        onView(isAssignableFrom(DatePicker.class))
                .perform(PickerActions.setDate(
                        now.getYear(),
                        now.getMonthValue(),
                        now.getDayOfMonth())
                );

        onView(withText(android.R.string.ok)).perform(click());

        onView(withId(R.id.news_item_publish_time_text_input_edit_text)).perform(click());
        onView(isAssignableFrom(TimePicker.class))
                .perform(PickerActions.setTime(
                        now.getHour(),
                        now.getMinute())
                );
        onView(withText(android.R.string.ok)).perform(click());
        onView(withId(R.id.news_item_description_text_input_edit_text)).perform(typeText("Hi"+now), ViewActions.closeSoftKeyboard());
        onView(withText(R.string.save)).perform(click());
    }

    @Step("Фильтрация новости по категории")
    public static void filterNewsByCategory(String found, String text){
        Allure.step("Фильтрация новости по категории");
        onView(withId(R.id.filter_news_material_button)).perform(click());
        onView(withId(R.id.news_item_category_text_auto_complete_text_view)).perform(click());
     //   onView(withId(R.id.text_input_end_icon)).perform(click());
        workPopup(found);
        onView(withId(R.id.filter_button)).perform(click());

        onView(isRoot()).perform(waitId(R.id.news_list_recycler_view, 10000));
        onView(ViewMatchers.withId(R.id.news_list_recycler_view))
                .perform(RecyclerViewActions.scrollTo(hasDescendant(withText(found+text))))
                .check(matches(isDisplayed()));
    }

    public static void clickOk(){
        onView(withText(android.R.string.ok)).perform(click());
    }

    public static void deleteNew(String text){
        onView(allOf(hasSibling(withText(text)), withId(R.id.delete_news_item_image_view))).perform(click());
    }

    public static void filterDate(LocalDateTime now){
        clickElement(R.id.filter_news_material_button);
        waitElement(R.id.news_item_publish_date_start_text_input_edit_text);
        clickElement(R.id.news_item_publish_date_start_text_input_edit_text);
        chooseDate(now);
        clickOk();
        clickElement(R.id.news_item_publish_date_end_text_input_edit_text);
        chooseDateEnd(now);
        clickOk();
        clickElement(R.id.filter_button);
    }

    public static void noNew(String text){
        try{scroll(R.id.news_list_recycler_view,(text));
        }catch (PerformException ex){
            foundElement(R.id.news_list_recycler_view);
        };
    }
}
