package com.commitfest.workmanagerwithroom.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commitfest.workmanagerwithroom.data.entity.PhotoEntity
import com.commitfest.workmanagerwithroom.domain.usecases.GetPhotosUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class PhotosViewModel(private val getPhotosUseCase: GetPhotosUseCase) : ViewModel() {
    private val _photosFlow = MutableStateFlow<Response<List<PhotoEntity>>>(Response.success(emptyList()))
    val photosFlow: StateFlow<Response<List<PhotoEntity>>> = _photosFlow

    fun getPhotos() {
        viewModelScope.launch {
            val response = getPhotosUseCase.execute()
            _photosFlow.emit(response)
        }
    }
}