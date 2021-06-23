package com.moataz.mox.data.model.medium

data class MediumResponse(
    val feed: Feed,
    val items: List<Item>,
    val status: String
)