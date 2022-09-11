package ru.mts.data.news.db

import androidx.room.*

@Dao
interface NewsDao {
    @Query("SELECT * FROM news")
    fun getAll(): List<NewsEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(news: NewsEntity?)
}