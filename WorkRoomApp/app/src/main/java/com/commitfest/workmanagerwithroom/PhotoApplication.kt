package com.commitfest.workmanagerwithroom

import android.app.Application
import com.commitfest.workmanagerwithroom.data.room.PhotoDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class PhotoApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { PhotoDatabase.getDatabase(this, applicationScope) }

}