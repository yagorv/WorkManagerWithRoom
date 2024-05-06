package com.commitfest.workmanagerwithroom.domain.usecases

import com.commitfest.workmanagerwithroom.data.api.FakeApi
import com.commitfest.workmanagerwithroom.data.entity.PhotoEntity
import retrofit2.Response

class GetPhotosUseCase {
    suspend fun execute(): Response<List<PhotoEntity>> {
        return FakeApi.apiService.getPhotos().execute()
    }
}