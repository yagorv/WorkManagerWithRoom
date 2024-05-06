package com.commitfest.workmanagerwithroom.data.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
open class PhotoEntity(
    @property:Json(name = "userId") open val userId: Long?,
    @property:Json(name = "id") open val id: Long?,
    @property:Json(name = "title") open val title: String?,
    @property:Json(name = "completed") open val completed: Boolean?,
)