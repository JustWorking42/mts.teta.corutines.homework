package ru.mts.data.news.db

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import ru.mts.data.main.AppDatabase
import ru.mts.data.utils.Result
import ru.mts.data.utils.VoidResult
import ru.mts.data.utils.runOperationCatching

class NewsLocalDataSource(private val context: Context) {
    suspend fun getNews(): Result<List<NewsEntity>, Throwable> {
        return runOperationCatching {
            withContext(Dispatchers.IO) {
                delay(1000L)
                val news = AppDatabase.getDatabase(context).newsDao().getAll() ?: emptyList()
                if (news.isEmpty()) throw ExpiredData()
                news
            }
        }
    }

    suspend fun setNews(news: List<NewsEntity>): VoidResult<Throwable> {
        return runOperationCatching {
            withContext(Dispatchers.IO) {
                AppDatabase.getDatabase(context).newsDao().run {
                    news.forEach {
                        insert(it)
                    }
                }
            }
        }
    }
}
