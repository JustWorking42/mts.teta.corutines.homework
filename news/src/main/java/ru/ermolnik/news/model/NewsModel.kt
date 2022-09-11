package ru.ermolnik.news.model

import ru.mts.data.news.db.NewsEntity

data class NewsModel(
    val id: Int,
    val title: String,
    val description: String,
    val iconUrl: String
)

fun NewsEntity.toModel() = NewsModel(
    id = this.id,
    title = this.title,
    description = this.description,
    iconUrl = ""
)