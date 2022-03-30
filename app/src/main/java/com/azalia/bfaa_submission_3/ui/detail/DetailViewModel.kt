package com.azalia.bfaa_submission_3.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.azalia.bfaa_submission_3.data.Repository
import com.azalia.bfaa_submission_3.model.User
import kotlinx.coroutines.*

class DetailViewModel(application: Application): AndroidViewModel(application) {
    val repository = Repository(application)

    suspend fun getDetailUser(username: String) = repository.getDetailUser(username)

    fun insertFavoriteUser(user: User) = viewModelScope.launch {
        repository.insertFavoriteUser(user)
    }

    fun deleteFavoriteUser(user: User) = viewModelScope.launch {
        repository.deleteFavoriteUser(user)
    }
}