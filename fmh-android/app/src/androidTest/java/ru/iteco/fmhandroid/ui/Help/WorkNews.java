package ru.iteco.fmhandroid.ui.Help;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static ru.iteco.fmhandroid.ui.Help.Helps.workPopup;
import static ru.iteco.fmhandroid.ui.Help.WaitId.waitId;

import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;

import java.time.LocalDateTime;

import ru.iteco.fmhandroid.R;

public class WorkNews {

    public static void createNews(String text, LocalDateTime now, String popup){
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

    public static void filterNewsByCategory(String found, String text){
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
}
