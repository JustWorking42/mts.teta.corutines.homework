package ru.mts.data.news.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.mts.data.main.NetworkClient
import ru.mts.data.utils.runOperationCatching
import ru.mts.data.utils.Result
import kotlin.random.Random

class NewsRemoteDataSource {
    suspend fun getNews(): Result<List<NewsDto.Response>, Throwable> {
        return runOperationCatching {
            withContext(Dispatchers.IO) {
                if (Random.nextInt(3) == 2) {
                    throw Throwable("Упс что то пошло не так")
                } else {
                    NetworkClient.create().getNews()
                }
            }
        }
    }
}