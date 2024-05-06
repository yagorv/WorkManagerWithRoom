package com.commitfest.workmanagerwithroom.data.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
open class PhotoEntity(
    @property:Json(name = "albumId") open val albumId: String?,
    @property:Json(name = "id") open val id: Double?,
    @property:Json(name = "title") open val title: Double?,
    @property:Json(name = "url") open val url: Double?,
    @property:Json(name = "thumbnailUrl") open val thumbnailUrl: Double?,
) {
    override fun equals(other: Any?): Boolean {
        if (other !is PhotoEntity) return false
        if (albumId != other.albumId) return false
        if (id != other.id) return false
        if (title != other.title) return false
        if (url != other.url) return false
        if (thumbnailUrl!= other.thumbnailUrl) return false
        return true
    }

    override fun hashCode(): Int {
        var result = albumId?.hashCode() ?: 0
        result = 31 * result + (id?.hashCode() ?: 0)
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + (url?.hashCode() ?: 0)
        result = 31 * result + (thumbnailUrl?.hashCode() ?: 0)
        return result
    }
}