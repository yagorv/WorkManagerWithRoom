package com.commitfest.workmanagerwithroom.domain.model

import com.commitfest.workmanagerwithroom.data.room.PhotosDBEntity

open class PhotoModel(
    open val photos: List<PhotosDBEntity> = emptyList(),
    open val id: String = "",
    open val albumId: String,
    open val title: String,
    open val url: String,
    open val thumbnailUrl: String
)