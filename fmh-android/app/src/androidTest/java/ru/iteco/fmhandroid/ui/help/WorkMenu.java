package ru.iteco.fmhandroid.ui.help;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static ru.iteco.fmhandroid.ui.help.Helps.authGood;
import static ru.iteco.fmhandroid.ui.help.Helps.clickElement;
import static ru.iteco.fmhandroid.ui.help.Helps.clickString;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;

public class WorkMenu {


    public static void openClaim(){
        Allure.step("Открытие страницы 'Заявки'");
        authGood();
        onView(withId(R.id.main_menu_image_button)).perform(click());
        onView(withText(R.string.claims)).perform(click());
    }

    public static void openNews(){
        Allure.step("Открытие страницы 'Новости'");
        authGood();
        onView(withId(R.id.main_menu_image_button)).perform(click());
        onView(withText(R.string.news)).perform(click());
    }

    public static void openAbout(){
        authGood();
        Allure.step("Переход к информации о хосписе");
        clickElement(R.id.main_menu_image_button);
        clickString(R.string.about);
    }

    public static void Main(){
        Allure.step("Переход на главную страницу");
        clickElement(R.id.main_menu_image_button);
        clickString(R.string.main);
    }

}
