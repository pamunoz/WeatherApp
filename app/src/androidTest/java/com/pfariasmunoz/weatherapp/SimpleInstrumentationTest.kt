package com.pfariasmunoz.weatherapp

import android.support.test.espresso.Espresso.*
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.replaceText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.BoundedMatcher
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.TextView
import com.pfariasmunoz.weatherapp.ui.activities.MainActivity
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test

class SimpleInstrumentationTest {
    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test fun itemClick_navigatesToDetail() {
        // Perform a click over the first position of the recycler
        onView(withId(R.id.forecastList)).perform(
                RecyclerViewActions
                        .actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        // Checks that is can find a view with a specific id and it is an instance of Textview
        onView(withId(R.id.weatherDescription))
                .check(matches(isAssignableFrom(TextView::class.java)))

    }

    @Test fun modifyZipCode_changesToolbarTitle() {
        // open the overflow
        openActionBarOverflowOrOptionsMenu(activityRule.activity)
        // find a view with the settings text and perform a click on it
        onView(withText(R.string.settings)).perform(click())
        // the setting activity is opened, so it will look for the EditText and
        // replace the old city code with a new one
        onView(withId(R.id.cityCode)).perform(replaceText("28830"))
        // press the back button. This will save the new value inside the preferences
        // and close the activity
        pressBack()
        // As onResume is executed in MainActivity, the request is performed again.
        // This will retrieve the forecast of the new city
        onView(isAssignableFrom(Toolbar::class.java))
                // Check the toolbar title and see whether it matches the proper value
                .check(matches(
                        withToolbarTitle(`is`("San Fernando de Henares (ES)"))))
    }

    private fun withToolbarTitle(textMatcher: Matcher<CharSequence>): Matcher<Any> =
            object : BoundedMatcher<Any, Toolbar>(Toolbar::class.java) {
                // This is where the check is done
                override fun matchesSafely(toolbar: Toolbar): Boolean {
                    return textMatcher.matches(toolbar.title)
                }
                // This add some info about the matcher
                override fun describeTo(description: Description) {
                    description.appendText("with toolbar title: ")
                    textMatcher.describeTo(description)
                }
            }
}