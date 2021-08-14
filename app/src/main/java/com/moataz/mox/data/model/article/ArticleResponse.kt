package com.moataz.mox.data.model.article

data class ArticleResponse(
    val feed: Feed?,
    val items: List<Item>?,
    val status: String?
)