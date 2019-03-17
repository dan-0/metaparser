package com.districtcommuter.metatagdemo

import androidx.test.espresso.idling.CountingIdlingResource

object IdlingResource {

    private const val MAIN_ACTIVITY_RESOURCE = "MainActivityResource"

    private val idlingResource = CountingIdlingResource(MAIN_ACTIVITY_RESOURCE)

    fun getMainActivityIdlingResource(): CountingIdlingResource {
        return idlingResource
    }
}