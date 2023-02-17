package ru.iteco.fmhandroid.ui;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;
import static ru.iteco.fmhandroid.ui.Help.auth;
import static ru.iteco.fmhandroid.ui.Help.authGood;
import static ru.iteco.fmhandroid.ui.Help.checkIfLogin;
import static ru.iteco.fmhandroid.ui.Help.withIndex;
import static ru.iteco.fmhandroid.ui.WaitId.waitId;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import ru.iteco.fmhandroid.R;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class Main {

    @Rule
    public ActivityTestRule<AppActivity> mActivityTestRule = new ActivityTestRule<>(AppActivity.class);

    @Before
    public void loginIfrequired() {
        if (!checkIfLogin()) {
            auth("login2", "password2");
            authGood();
        }
    }

    @Test
    public void allClaim(){
        onView(withText(R.string.all_news)).perform(click());
        onView(withId(R.id.all_news_cards_block_constraint_layout)).check(matches(isDisplayed()));
    }

    @Test
    public void claimView(){
        onView(withIndex(withId(R.id.claim_list_card), 0)).perform(click());
        onView(isRoot()).perform(waitId(R.id.container_custom_app_bar_include_on_fragment_open_claim, 5000)).check(matches(isDisplayed()));
    }



}
