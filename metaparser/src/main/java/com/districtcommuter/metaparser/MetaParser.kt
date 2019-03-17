package com.districtcommuter.metaparser

import java.io.IOException

interface MetaParser {

    /**
     * Parses given [url] with a [timeout] in millis for meta data returning a [MetaParserData] with the result.
     *
     * Note: This function works and returns on the thread it is called from, the caller should ensure proper
     *  concurrency
     *
     * Throws [IOException] on timeout
     */
    @Throws(IOException::class)
    fun parseWebsite(url: String, timeout: Int) : MetaParserData
}