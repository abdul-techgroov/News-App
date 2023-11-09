package com.news.app.model

import com.squareup.moshi.Json

data class NewsData(
    @field:Json(name = "title") val title: String? = "",
    @field:Json(name = "description") val description: String? = "",
    @field:Json(name = "urlToImage") val imageUrl: String? = "",
)
