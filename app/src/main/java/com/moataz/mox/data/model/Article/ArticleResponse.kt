package com.moataz.mox.data.model.Article

data class ArticleResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)