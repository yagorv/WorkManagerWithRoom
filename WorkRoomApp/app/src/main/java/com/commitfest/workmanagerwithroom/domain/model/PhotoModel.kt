package com.commitfest.workmanagerwithroom.domain.model

import com.commitfest.workmanagerwithroom.data.room.Photos

open class PhotoModel(
    open val photos: List<Photos> = emptyList(),
    open val id: String = "",
    open val albumId: String,
    open val title: String,
    open val url: String,
    open val thumbnailUrl: String
)