package com.districtcommuter.metaparser

/**
 * Data object representing data parsed from a website.
 *
 * @param url Website's URL
 * @param imageUrl URL for the meta tag image
 * @param title Title derived from head data
 * @param type Open Graph type
 * @param description Description found in meta data
 */
data class MetaParserData(
    val url: String? = "",
    val imageUrl: String? = "",
    val title: String? = "",
    val type: String? = "",
    val description: String? = ""
)