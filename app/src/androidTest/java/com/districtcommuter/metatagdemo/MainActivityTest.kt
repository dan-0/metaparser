package com.districtcommuter.metatagdemo

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun ensureDataFetched() {
        val idlingResource = IdlingResource.getMainActivityIdlingResource()
        IdlingRegistry.getInstance().register(idlingResource)

        onView(withId(R.id.titleResult))
            .check(matches(withText("Introducing a new Google Play app and game icon specification")))

        onView(withId(R.id.urlResult))
            .check(matches(withText("https://android-developers.googleblog.com/2019/03/introducing-new-google-play-app-and.html")))

        onView(withId(R.id.imageUrlResult))
            .check(matches(withText("https://1.bp.blogspot.com/-VGNBAJbKTk0/XIrT0ARxwrI/AAAAAAAAHVw/fQJEOq59Crc1YzbfT_OhNfXwipaxQ_yCwCLcBGAs/w1200-h630-p-k-no-nu/image1.png")))

        onView(withId(R.id.typeResult))
            .check(matches(withText("article")))

        onView(withId(R.id.descriptionResult))
            .check(matches(withText("As part of our focus and dedication to improving the Google Play Store experience for our users, we are introducing new design specifications for your app icons.")))
    }
}