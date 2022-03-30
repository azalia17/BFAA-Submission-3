package com.azalia.bfaa_submission_3.ui.splash

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import com.azalia.bfaa_submission_3.data.Repository
import kotlinx.coroutines.Dispatchers

class SplashScreenViewModel(application: Application): AndroidViewModel(application) {

    private val repository = Repository(application)

    fun getThemeSetting() = repository.getThemeSetting().asLiveData(Dispatchers.IO)

}