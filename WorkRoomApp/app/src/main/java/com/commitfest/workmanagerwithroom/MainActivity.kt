package com.commitfest.workmanagerwithroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.commitfest.workmanagerwithroom.data.room.PhotoDatabase
import com.commitfest.workmanagerwithroom.data.room.PhotosDAO
import com.commitfest.workmanagerwithroom.domain.model.PhotoModel
import com.commitfest.workmanagerwithroom.domain.usecases.GetPhotosUseCase
import com.commitfest.workmanagerwithroom.presentation.PhotosViewModel
import com.commitfest.workmanagerwithroom.ui.theme.MyApplicationTheme
import com.commitfest.workmanagerwithroom.workers.GetDataWorker
import com.commitfest.workmanagerwithroom.workers.UpdateDataWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    private lateinit var photosViewModel: PhotosViewModel
    private lateinit var getPhotosUseCase: GetPhotosUseCase
    private lateinit var photosDAO: PhotosDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // getPhotosUseCase = GetPhotosUseCase()

      //  val photoDatabase = PhotoDatabase
       /// photosDAO = photoDatabase.photoDao()

        photosViewModel = ViewModelProvider(this)[PhotosViewModel::class.java]
        photosViewModel.getPhotos()

        val photos = mutableStateOf<List<PhotoModel>>(listOf())

        lifecycleScope.launch {
            photosViewModel.photosFlow.collect { response ->
                withContext(Dispatchers.IO) {
                    if (response.isSuccessful) {
                        response.body()?.let { photoList ->
                            photos.value = photoList
                        }
                    }
                }
            }
        }

        setContent {
            Content(photos.value)
        }
    }

    override fun onStart() {
        super.onStart()
        // This worker are fake and only simulate obtaining data
        // In future this could save some data on Room instance
        enqueueData(createOneTimeGetDataWorkerRequest(delay = 1))
        enqueueData(createPeriodicUpdateDataWorkerRequest(delay = 3))
    }

    override fun onResume() {
        super.onResume()
        // here we could do obtain data from room to show it
    }

    private fun enqueueData(request: WorkRequest) {
        WorkManager
            .getInstance(this)
            .enqueue(request)
    }

    private fun createOneTimeGetDataWorkerRequest(
        delay: Long,
        timeUnit: TimeUnit = TimeUnit.MINUTES
    ): WorkRequest =
        OneTimeWorkRequestBuilder<GetDataWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiresBatteryNotLow(true)
                    .build()
            )
            .setInitialDelay(delay, timeUnit).build()

    private fun createPeriodicUpdateDataWorkerRequest(
        periodicityInterval: Long = 16, // Minimum accepted interval are 15 minutes
        delay: Long = 1,
        timeUnit: TimeUnit = TimeUnit.MINUTES
    ): WorkRequest =
        PeriodicWorkRequestBuilder<UpdateDataWorker>(periodicityInterval, timeUnit)
            .setConstraints(
                Constraints.Builder()
                    .setRequiresStorageNotLow(true)
                    .build()
            )
            .setInitialDelay(delay, timeUnit)
            .build()

    @Composable
    fun Content(photos: List<PhotoModel>) {
        MyApplicationTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Text(
                    text = "Hello!"
                )
                PhotoList(photos = photos)

            }
        }
    }

    @Composable
    fun PhotoList(photos: List<PhotoModel>) {
        LazyColumn {
            items(photos.size) { index ->
                Text(text = photos[index].title)
            }
        }
    }
}

