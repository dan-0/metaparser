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

package me.danlowe.metaparser

import org.jsoup.Jsoup
import java.net.URL

internal class MetaParserImpl: MetaParser {

    companion object {
        private const val OG_TITLE = "og:title"
        private const val OG_TYPE = "og:type"
        private const val OG_IMAGE = "og:image"
        private const val OG_DESCRIPTION = "og:description"
    }

    override fun parseWebsite(url: String, timeout: Int): MetaParserData {

        val doc = Jsoup.parse(URL(url), timeout)
        val metaTags = doc.getElementsByTag("meta")

        var title: String? = null
        var imageUrl: String? = null
        var type: String? = null
        var description: String? = null
        metaTags.forEach {
            val content: String? = it.attr("content")
            val property: String? = it.attr("property")

            when (property) {
                OG_IMAGE -> imageUrl = content
                OG_TITLE -> title = content
                OG_TYPE -> type = content
                OG_DESCRIPTION -> description = content
            }
        }

        if (title == null) {
            val titleElement = doc.getElementsByTag("title").first()
            title = titleElement?.text()
        }

        return MetaParserData(
            url = url,
            imageUrl = imageUrl,
            title = title,
            type = type,
            description = description
        )
    }
}