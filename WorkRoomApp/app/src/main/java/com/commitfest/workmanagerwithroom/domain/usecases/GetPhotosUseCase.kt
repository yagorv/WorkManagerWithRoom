package com.commitfest.workmanagerwithroom.domain.usecases

import com.commitfest.workmanagerwithroom.data.api.FakeApi
import com.commitfest.workmanagerwithroom.data.entity.PhotoEntity
import com.commitfest.workmanagerwithroom.data.mapper.PhotoMapper
import com.commitfest.workmanagerwithroom.domain.model.PhotoModel
import retrofit2.Response

class GetPhotosUseCase(private val photoMapper: PhotoMapper) {
    suspend fun execute(): Response<List<PhotoModel>> {
        val response = FakeApi.apiService.getPhotos().execute()
        return if (response.isSuccessful) {
            Response.success(response.body()?.map { photoMapper.toModel(it)})
        } else {
            Response.error(response.code(), response.errorBody()!!)
        }
    }
}