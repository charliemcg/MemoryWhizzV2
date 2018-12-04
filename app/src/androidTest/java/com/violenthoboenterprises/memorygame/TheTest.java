package com.violenthoboenterprises.memorygame;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.Button;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class TheTest {

    @Rule public ActivityTestRule<MainActivity> testRule
            = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickMusicButton(){
        onView(withId(R.id.splashMusic)).perform(click());
        onView(withId(R.id.splashMusic)).check(matches(withText("MUSIC: OFF")));
    }

}
