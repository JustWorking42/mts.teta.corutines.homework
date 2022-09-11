package ru.mts.data.news.remote

import retrofit2.http.GET
import retrofit2.http.Headers

interface NewsApiService {

    @GET("api/v1/sample")
    @Headers("Content-Type:application/json; charset=utf-8;")
    suspend fun getNews(): List<NewsDto.Response>
}
