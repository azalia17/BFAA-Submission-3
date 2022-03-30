package com.azalia.bfaa_submission_3.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.azalia.bfaa_submission_3.R
import com.azalia.bfaa_submission_3.data.Resource
import com.azalia.bfaa_submission_3.databinding.ActivityDetailBinding
import com.azalia.bfaa_submission_3.model.User
import com.azalia.bfaa_submission_3.ui.adapter.FollowPagerAdapter
import com.azalia.bfaa_submission_3.util.Constanta.EXTRA_USER
import com.azalia.bfaa_submission_3.util.Constanta.TAB_TITLES
import com.azalia.bfaa_submission_3.util.ViewStateCallback
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.*

class DetailActivity : AppCompatActivity(), ViewStateCallback<User?> {

    private lateinit var detailBinding: ActivityDetailBinding
    private val viewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            elevation = 0f
        }

        val username = intent.getStringExtra(EXTRA_USER)

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getDetailUser(username.toString()).observe(this@DetailActivity, {
                when (it) {
                    is Resource.Failure -> onFailed(it.message)
                    is Resource.Loading -> onLoading()
                    is Resource.Success -> onSuccess(it.data)
                }
            })
        }

        val pagerAdapter = FollowPagerAdapter(this, username.toString())

        detailBinding.apply {
            viewPager.adapter = pagerAdapter
            TabLayoutMediator(tabs, viewPager) { tabs, position ->
                tabs.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onSuccess(data: User?) {
        detailBinding.apply {
            tvTotalRepo.text = data?.repository.toString()
            tvTotalFollower.text = data?.follower.toString()
            tvTotalFollowing.text = data?.following.toString()
            tvNameDetail.text = data?.name
            tvUsernameDetail.text = data?.username
            tvCompanyDetail.text = data?.company
            tvLocationDetail.text = data?.location

            Glide.with(this@DetailActivity)
                .load(data?.avatar)
                .apply(RequestOptions.circleCropTransform())
                .into(detailBinding.ivAvatar)

           ivFavorite.visibility = View.VISIBLE

            if (data?.isFavorite == true)
                ivFavorite.setImageDrawable(resources.getDrawable(R.drawable.ic_favorite))
            else
                ivFavorite.setImageDrawable(resources.getDrawable(R.drawable.ic_favorite_border))

            supportActionBar?.title = data?.username

            ivFavorite.setOnClickListener {
                if (data?.isFavorite == true) {
                    data.isFavorite = false
                    viewModel.deleteFavoriteUser(data)
                    ivFavorite.setImageDrawable(resources.getDrawable(R.drawable.ic_favorite_border))
                } else {
                    data?.isFavorite = true
                    data?.let { it1 -> viewModel.insertFavoriteUser(it1) }
                    ivFavorite.setImageDrawable(resources.getDrawable(R.drawable.ic_favorite))
                }
            }
        }
    }

    override fun onLoading() {
        detailBinding.ivFavorite.visibility = View.INVISIBLE
    }

    override fun onFailed(message: String?) {
        detailBinding.ivFavorite.visibility = View.INVISIBLE
    }
}