package com.azalia.bfaa_submission_3.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.azalia.bfaa_submission_3.data.Repository

class FavoriteViewModel(application: Application): AndroidViewModel(application) {
    private val repository = Repository(application)

    suspend fun getFavoriteList() = repository.getFavoriteList()
}