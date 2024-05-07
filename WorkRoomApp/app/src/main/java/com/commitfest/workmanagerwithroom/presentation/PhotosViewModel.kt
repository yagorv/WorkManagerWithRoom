package com.commitfest.workmanagerwithroom.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commitfest.workmanagerwithroom.data.room.Events
import com.commitfest.workmanagerwithroom.data.room.PhotosDAO
import com.commitfest.workmanagerwithroom.domain.model.PhotoModel
import com.commitfest.workmanagerwithroom.domain.usecases.GetPhotosUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class PhotosViewModel(
    private val getPhotosUseCase: GetPhotosUseCase,
    private val dao: PhotosDAO
) : ViewModel() {
    private val _photosFlow = MutableStateFlow<Response<List<PhotoModel>>>(Response.success(emptyList()))
    val photosFlow: StateFlow<Response<List<PhotoModel>>> = _photosFlow

    fun getPhotos() {
        viewModelScope.launch {
            val response = getPhotosUseCase.execute()
            _photosFlow.emit(response)
        }
    }

    fun onEvent(event: Events) {
        when(event) {
            is Events.DeletePhotos -> {
                viewModelScope.launch {
                    dao.deletePhotos()
                }
            }
            is Events.SavePhotosInRoom -> {
                viewModelScope.launch {
                    photosFlow.collect { response ->
                        if (response.isSuccessful) {
                            response.body()?.let { photos ->
                                dao.savePhotos(photos)
                            }
                        }
                    }
                }
            }
            else -> {}
        }
    }
}