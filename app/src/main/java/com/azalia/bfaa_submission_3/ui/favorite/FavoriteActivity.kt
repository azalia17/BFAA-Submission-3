package com.azalia.bfaa_submission_3.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.azalia.bfaa_submission_3.R
import com.azalia.bfaa_submission_3.data.Repository
import com.azalia.bfaa_submission_3.data.Resource
import com.azalia.bfaa_submission_3.data.local.UserDao
import com.azalia.bfaa_submission_3.databinding.ActivityFavoriteBinding
import com.azalia.bfaa_submission_3.model.User
import com.azalia.bfaa_submission_3.ui.adapter.UserAdapter
import com.azalia.bfaa_submission_3.util.ViewStateCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity(), ViewStateCallback<List<User>> {

    private lateinit var favoriteBinding: ActivityFavoriteBinding
    private lateinit var userAdapter: UserAdapter
    private val viewModel: FavoriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(favoriteBinding.root)

        userAdapter = UserAdapter()

        favoriteBinding.rvFavorite.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(this@FavoriteActivity, LinearLayoutManager.VERTICAL, false)
        }

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getFavoriteList().observe(this@FavoriteActivity, {
                when(it) {
                    is Resource.Failure -> onFailed(it.message)
                    is Resource.Loading -> onLoading()
                    is Resource.Success -> it.data?.let { it1 -> onSuccess(it1) }
                }
            })
        }
    }

    override fun onSuccess(data: List<User>) {
        favoriteBinding.apply {
            favoriteProgressBar.visibility = invisible
        }
        userAdapter.setAllData(data)
    }

    override fun onLoading() {
        favoriteBinding.apply {
            favoriteProgressBar.visibility = visible
        }
    }


    override fun onFailed(message: String?) {
        if (message == null) {
            favoriteBinding.apply {
                favoriteProgressBar.visibility = invisible
                tvFavoriteMessage.text = "Go add new favorite"
                rvFavorite.visibility = invisible
            }
        }
    }

    override fun onResume() {
        super.onResume()
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getFavoriteList().observe(this@FavoriteActivity, {
                when(it) {
                    is Resource.Failure -> onFailed(it.message)
                    is Resource.Loading -> onLoading()
                    is Resource.Success -> it.data?.let { it1 -> onSuccess(it1) }
                }
            })
        }
    }
}