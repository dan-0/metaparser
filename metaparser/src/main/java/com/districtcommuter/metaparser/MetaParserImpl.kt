package com.districtcommuter.metaparser

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