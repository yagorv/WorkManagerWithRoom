package com.commitfest.workmanagerwithroom.data.mapper

import com.commitfest.workmanagerwithroom.data.entity.PhotoEntity
import com.commitfest.workmanagerwithroom.domain.model.PhotoModel

open class PhotoMapper {
    open fun toModel(entity: PhotoEntity) =
        with(entity) {
            PhotoModel(
                id = id.toString (),
            albumId = albumId ?: "",
            title = title ?: "",
            url = url ?: "",
            thumbnailUrl = thumbnailUrl ?: ""
            )
        }
}