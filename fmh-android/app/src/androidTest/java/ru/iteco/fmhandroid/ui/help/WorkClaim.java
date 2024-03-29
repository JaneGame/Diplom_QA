package ru.iteco.fmhandroid.ui.help;

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

import static ru.iteco.fmhandroid.ui.help.Helps.waitElement;
import static ru.iteco.fmhandroid.ui.help.WaitId.waitId;

import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;

import java.time.LocalDateTime;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;

public class WorkClaim {

    public static void createClaimBefore(String text){
        Allure.step("Создание заявки до ввода исполнителя");
        waitElement(R.id.add_new_claim_material_button);
        onView(withId(R.id.add_new_claim_material_button)).perform(click());
        onView(withId(R.id.title_edit_text)).perform(typeText(text));
    }

    public static void foundClaim(String text){
        Allure.step("Поиск заявки");
        onView(isRoot()).perform(waitId(R.id.claim_list_swipe_refresh, 25000)).check(matches(isDisplayed()));
        onView(isRoot()).perform(waitId(R.id.claim_list_card, 25000)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.claim_list_recycler_view))
                .perform(RecyclerViewActions.scrollTo(hasDescendant(withText(text))))
                .perform(click());
        onView(isRoot()).perform(waitId(R.id.status_icon_image_view, 10000)).check(matches(isDisplayed()));
    }

    public static void createClaimAfter(LocalDateTime now){
        Allure.step("Создание заявки после ввода исполнителя");
        onView(withId(R.id.date_in_plan_text_input_layout)).perform(click());
        onView(isAssignableFrom(DatePicker.class))
                .perform(PickerActions.setDate(
                        now.getYear(),
                        now.getMonthValue(),
                        now.getDayOfMonth())
                );

        onView(withText(android.R.string.ok)).perform(click());

        onView(withId(R.id.time_in_plan_text_input_layout)).perform(click());

        onView(isAssignableFrom(TimePicker.class))
                .perform(PickerActions.setTime(
                        now.getHour(),
                        now.getMinute())
                );
        onView(withText(android.R.string.ok)).perform(click());

        onView(withId(R.id.description_edit_text)).perform(typeText("Hi"+now), closeSoftKeyboard());

        onView(withId(R.id.save_button)).perform(click());
}}
