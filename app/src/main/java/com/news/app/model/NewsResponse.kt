package com.news.app.model

import com.squareup.moshi.Json

data class NewsResponse(
    @field:Json(name = "status") val status: String? = "",
    @field:Json(name = "message") val message: String? = "",
    @field:Json(name = "articles") val articles: MutableList<NewsData>? = null,
)
