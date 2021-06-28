package com.moataz.mox.data.model.article

data class MediumResponse(
    val feed: Feed?,
    val items: List<Item>?,
    val status: String?
)