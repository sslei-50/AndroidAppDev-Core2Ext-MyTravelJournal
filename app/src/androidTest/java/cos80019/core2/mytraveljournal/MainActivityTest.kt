package cos80019.core2.mytraveljournal

import android.service.autofill.FieldClassification
import android.widget.ImageView
import android.widget.RatingBar
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import cos80019.core2.mytraveljournal.R.id.butchartTitle_textV
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.regex.Pattern.matches


class MainActivityTest {

    private lateinit var titleStr: String

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testMainToSecondToMain() {
        //test switching activities: from Main to Second and back to Main
        onView(withId(R.id.butchart_imageV)).perform(click())
        onView(withId(R.id.second)).check(ViewAssertions.matches(isDisplayed()))
        onView(isRoot()).perform(pressBack())
        onView(withId(R.id.main)).check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun TestNameChanged() {
        // open Main Activity
        // click on first image to launch Second Activity
        // edit name of the first image to "NameChanged" on the Second Activity
        // press back button to go back to the Main Activity
        // name of the first location displayed on the Main Activity should be changed to "NameChanged"

        //expected string after name being changed
        titleStr = "NameChanged\n4.5 Stars"

        //click on image of the first location to launch Second Activity
        onView(withId(R.id.butchart_imageV)).perform(click())

        //change the name of the first location to "NameChanged" on the Second Activity View
        onView(withId(R.id.location_ET)).perform(clearText(), typeText("NameChanged"))

        //press the "Back" button to go back to the Main Activity
        onView(isRoot()).perform(pressBack())

        //test to see if details of the first location displayed on the Main Activity reflected the expected name change
        val textView = onView(withId(R.id.butchartTitle_textV))
        textView.check(ViewAssertions.matches(withText(titleStr)))

    }


}

