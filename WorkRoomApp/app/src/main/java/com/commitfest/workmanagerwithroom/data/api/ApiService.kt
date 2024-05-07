package com.commitfest.workmanagerwithroom.data.api

import com.commitfest.workmanagerwithroom.data.entity.PhotoEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("photos")
    fun getPhotos(): Call<List<PhotoEntity>>
}