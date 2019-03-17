package com.districtcommuter.metatagdemo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.districtcommuter.metaparser.MetaParserBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private val job: Job = Job()



    private val testUrl = "https://android-developers.googleblog.com/2019/03/introducing-new-google-play-app-and.html"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadSite(testUrl)
    }

    private fun loadSite(url: String) {
        val parser = MetaParserBuilder().build()
        IdlingResource.getMainActivityIdlingResource().increment()
        CoroutineScope(job + Dispatchers.IO).launch {
            val data = try {
                parser.parseWebsite(url, 5000)
            } catch (e: IOException) {
                Log.e(TAG, "Timeout occurred")
                return@launch
            }

            withContext(job + Dispatchers.Main) {
                titleResult.text = data.title
                urlResult.text = data.url
                imageUrlResult.text = data.imageUrl
                typeResult.text = data.type
                descriptionResult.text = data.description
            }
        }.invokeOnCompletion { IdlingResource.getMainActivityIdlingResource().decrement() }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.let {
            if (!it.isCancelled) {
                it.cancel()
            }
        }
    }
}
