package ru.mts.data.news.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import ru.mts.data.news.db.NewsEntity
import ru.mts.data.news.db.NewsLocalDataSource
import ru.mts.data.news.remote.NewsRemoteDataSource
import ru.mts.data.news.remote.toDomain
import ru.mts.data.utils.Result
import ru.mts.data.utils.doOnError
import ru.mts.data.utils.doOnSuccess

class NewsRepository(
    private val newsLocalDataSource: NewsLocalDataSource,
    private val newsRemoteDataSource: NewsRemoteDataSource
) {

    suspend fun getNews(isForceUpdate: Boolean = false): Flow<Result<List<NewsEntity>, Throwable>> {
        return if (isForceUpdate) {
            getRemote()
        } else {
            getCached()
        }
    }

    private suspend fun getCached(): Flow<Result<List<NewsEntity>, Throwable>> {
        return flow {
            newsLocalDataSource.getNews()
                .doOnError {
                    getRemote().collect {
                        emit(it)
                    }
                }
                .doOnSuccess {
                    emit(Result.Success(it))
                }
        }
    }

    private suspend fun getRemote(): Flow<Result<List<NewsEntity>, Throwable>> {
        return flow {
            newsRemoteDataSource.getNews()
                .doOnError {
                    emit(Result.Error(it))
                }
                .doOnSuccess { response ->
                    val newsEntityList = response.map { it.toDomain() }
                    save(newsEntityList)
                    emit(Result.Success(newsEntityList))
                }
        }
    }

    private suspend fun save(newsEntityList: List<NewsEntity>) {
        newsLocalDataSource.setNews(newsEntityList)
    }
}
