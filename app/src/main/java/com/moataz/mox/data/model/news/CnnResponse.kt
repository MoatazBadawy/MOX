package com.moataz.mox.data.model.news

data class CnnResponse(
    val feed: Feed?,
    val items: List<Item>?,
    val status: String?
)