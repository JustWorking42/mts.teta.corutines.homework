package ru.mts.data.news.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import ru.mts.data.news.db.NewsEntity


class NewsDto {
    @Parcelize
    data class Response(
        @SerializedName("id")
        val id: Int,
        @SerializedName("title")
        val title: String,
        @SerializedName("description")
        val description: String
    ) : Parcelable
}

internal fun NewsDto.Response.toDomain(): NewsEntity {
    return NewsEntity(
        id = this.id,
        title = this.title,
        description = this.description
    )
}