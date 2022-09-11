package ru.ermolnik.news

import ru.ermolnik.news.model.NewsModel

sealed class NewsState {
    object Loading: NewsState()
    data class Error(val throwable: Throwable): NewsState()
    data class Content(val newsModel: List<NewsModel>): NewsState()
}