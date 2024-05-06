package com.commitfest.workmanagerwithroom.data.api

import com.commitfest.workmanagerwithroom.data.entity.PhotoEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("posts/{id}")
    fun getPostById(@Path("id") postId: Int): List<PhotoEntity>
    @GET("photos")
    fun getPhotos(): Call<List<PhotoEntity>>
}