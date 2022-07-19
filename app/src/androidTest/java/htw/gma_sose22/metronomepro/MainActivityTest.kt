package htw.gma_sose22.metronomepro


import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import htw.gma_sose22.R
import htw.gma_sose22.metronomekit.metronome.MetronomeService
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun before() {
        val context: Context = ApplicationProvider.getApplicationContext()
        context.deleteDatabase("pattern_database")
    }

    @Test
    fun testSavePatternFlow() {
        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.navigation_library), withContentDescription("Library"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_view),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        val floatingActionButton = onView(
            allOf(
                withId(R.id.fab), withContentDescription("Add new pattern"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        floatingActionButton.perform(click())

        val textView = onView(
            allOf(
                withText("Editor"),
                withParent(
                    allOf(
                        withId(R.id.topAppBar),
                        withParent(withId(R.id.app_bar_layout))
                    )
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Editor")))

        val floatingActionButton2 = onView(
            allOf(
                withId(R.id.fab), withContentDescription("Add new beat"),
                childAtPosition(
                    allOf(
                        withId(R.id.container),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        floatingActionButton2.perform(click())

        val actionMenuItemView = onView(
            allOf(
                withId(R.id.save_pattern), withContentDescription("Save pattern"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.topAppBar),
                        2
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        actionMenuItemView.perform(click())

        val textInputEditText = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.dialog_text_input_layout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText("Test Pattern"), closeSoftKeyboard())

        val materialButton = onView(
            allOf(
                withId(android.R.id.button1), withText("OK"),
                childAtPosition(
                    childAtPosition(
                        withId(androidx.appcompat.R.id.buttonPanel),
                        0
                    ),
                    3
                )
            )
        )
        materialButton.perform(scrollTo(), click())

        val textView3 = onView(
            allOf(
                withId(R.id.titleTextView), withText("Test Pattern"),
                withParent(withParent(IsInstanceOf.instanceOf(androidx.cardview.widget.CardView::class.java))),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("Test Pattern")))
    }

    @Test
    fun metronomeFragmentTest() {
        val materialButton = onView(
            allOf(
                withId(R.id.button),
                childAtPosition(
                    allOf(
                        withId(R.id.beat_button_3),
                        childAtPosition(
                            withId(R.id.beat_buttons),
                            2
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        val materialButton2 = onView(
            allOf(
                withId(R.id.button),
                childAtPosition(
                    allOf(
                        withId(R.id.beat_button_4),
                        childAtPosition(
                            withId(R.id.beat_buttons),
                            3
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton2.perform(click())

        val materialButton3 = onView(
            allOf(
                withId(R.id.button),
                childAtPosition(
                    allOf(
                        withId(R.id.beat_button_4),
                        childAtPosition(
                            withId(R.id.beat_buttons),
                            3
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton3.perform(click())

        val materialButton4 = onView(
            allOf(
                withId(R.id.tones_increment_button), withContentDescription("Add note"),
                childAtPosition(
                    allOf(
                        withId(R.id.linearLayout),
                        childAtPosition(
                            withId(R.id.tones_view),
                            0
                        )
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        materialButton4.perform(click())

        val viewGroup = onView(
            allOf(
                withId(R.id.beat_button_5),
                withParent(
                    allOf(
                        withId(R.id.beat_buttons),
                        withParent(withId(R.id.tones_view))
                    )
                ),
                isDisplayed()
            )
        )
        viewGroup.check(matches(isDisplayed()))

        val textView = onView(
            allOf(
                withId(R.id.tones_text_view), withText("5"),
                withParent(
                    allOf(
                        withId(R.id.linearLayout),
                        withParent(withId(R.id.tones_view))
                    )
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("5")))

        assertEquals(false, MetronomeService.isPlaying)

        val materialButton5 = onView(
            allOf(
                withId(R.id.button_start_stop), withText("Start"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton5.perform(click())

        assertEquals(true, MetronomeService.isPlaying)

        val button = onView(
            allOf(
                withId(R.id.button_start_stop), withText("Stop"),
                withParent(withParent(withId(R.id.nav_host_fragment))),
                isDisplayed()
            )
        )
        button.check(matches(isDisplayed()))

        val button2 = onView(
            allOf(
                withId(R.id.tones_decrement_button), withContentDescription("Remove note"),
                withParent(
                    allOf(
                        withId(R.id.linearLayout),
                        withParent(withId(R.id.tones_view))
                    )
                ),
                isDisplayed()
            )
        )
        button2.check(matches(isDisplayed()))

        val button3 = onView(
            allOf(
                withId(R.id.tones_increment_button), withContentDescription("Add note"),
                withParent(
                    allOf(
                        withId(R.id.linearLayout),
                        withParent(withId(R.id.tones_view))
                    )
                ),
                isDisplayed()
            )
        )
        button3.check(matches(isDisplayed()))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
