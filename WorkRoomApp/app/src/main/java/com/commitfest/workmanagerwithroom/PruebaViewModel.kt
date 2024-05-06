package com.commitfest.workmanagerwithroom

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.util.UUID

class PruebaViewModel: ViewModel() {

    var workerId: UUID? by mutableStateOf(null)
        private set
}