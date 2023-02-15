package ru.iteco.fmhandroid.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static ru.iteco.fmhandroid.ui.WaitId.waitId;

import android.widget.DatePicker;

import androidx.test.espresso.contrib.PickerActions;

import org.junit.Test;

import java.time.LocalDateTime;

import ru.iteco.fmhandroid.R;

public class News {


    @Test
    public void filterNewsByDate() {

        onView(withId(R.id.main_menu_image_button)).perform(click());

        onView(withText(R.string.news)).perform(click());

        onView(withId(R.id.filter_news_material_button)).perform(click());

        onView(withId(R.id.news_item_publish_date_start_text_input_edit_text)).perform(click());

        LocalDateTime now = LocalDateTime.now();

        onView(isAssignableFrom(DatePicker.class))
                .perform(PickerActions.setDate(
                        now.getYear(),
                        now.getMonthValue(),
                        //for example choosing 1 day gap from today
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

    }
}
