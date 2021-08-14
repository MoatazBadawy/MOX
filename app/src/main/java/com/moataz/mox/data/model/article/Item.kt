package com.moataz.mox.data.model.article

import com.moataz.mox.data.model.article.Enclosure

data class Item(
    val author: String?,
    val categories: List<Any>?,
    val content: String?,
    val description: String?,
    val enclosure: Enclosure?,
    val guid: String?,
    val link: String?,
    val pubDate: String?,
    val thumbnail: String?,
    val title: String?,
    val favStatus: Boolean?
)