package com.districtcommuter.metatagdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.districtcommuter.metaparser.MetaParserBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    var job: Job = Job()

    val testUrl = "https://android-developers.googleblog.com/2019/03/introducing-new-google-play-app-and.html"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadSite(testUrl)
    }

    private fun loadSite(url: String) {
        val parser = MetaParserBuilder().build()
        CoroutineScope(job + Dispatchers.IO).launch {
            val data = parser.parseWebsite(url, 5000)
            withContext(job + Dispatchers.Main) {
                titleResult.text = data.title
                urlResult.text = data.url
                imageUrlResult.text = data.imageUrl
                typeResult.text = data.type
                descriptionResult.text = data.description
            }
        }
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
