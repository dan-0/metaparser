/*
 * MIT License
 *
 * Copyright (c) 2019 Dan Lowe
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

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
