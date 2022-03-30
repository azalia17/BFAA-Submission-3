package com.azalia.bfaa_submission_3.ui.following

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.azalia.bfaa_submission_3.data.Repository
import com.azalia.bfaa_submission_3.data.Resource
import com.azalia.bfaa_submission_3.data.remote.RetrofitService
import com.azalia.bfaa_submission_3.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel(application: Application): AndroidViewModel(application) {

    private val repository = Repository(application)

    fun getUserFollowing(username: String) = repository.getUserFollowing(username)
}