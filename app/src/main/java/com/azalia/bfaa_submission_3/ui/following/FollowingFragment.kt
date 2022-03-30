package com.azalia.bfaa_submission_3.ui.following

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.azalia.bfaa_submission_3.R
import com.azalia.bfaa_submission_3.data.Resource
import com.azalia.bfaa_submission_3.databinding.FollowerFragmentBinding
import com.azalia.bfaa_submission_3.model.User
import com.azalia.bfaa_submission_3.ui.adapter.UserAdapter
import com.azalia.bfaa_submission_3.util.ViewStateCallback

class FollowingFragment : Fragment(), ViewStateCallback<List<User>> {

    private lateinit var followingFragmentBinding: FollowerFragmentBinding
    private val viewModel: FollowingViewModel by viewModels()
    private lateinit var userAdapter: UserAdapter
    private var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(KEY_BUNDLE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        followingFragmentBinding = FollowerFragmentBinding.inflate(inflater, container, false)
        return followingFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userAdapter = UserAdapter()
        followingFragmentBinding.rvListFollow.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        viewModel.getUserFollowing(username.toString()).observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Failure -> onFailed(it.message)
                is Resource.Loading -> onLoading()
                is Resource.Success -> it.data?.let { it1 -> onSuccess(it1) }
            }
        })
    }

    override fun onSuccess(data: List<User>) {
        userAdapter.setAllData(data)

        followingFragmentBinding.apply {
            tvMessage.visibility = invisible
            followProgressBar.visibility = invisible
            rvListFollow.visibility = visible
        }
    }

    override fun onLoading() {
        followingFragmentBinding.apply {
            tvMessage.visibility = invisible
            followProgressBar.visibility = visible
            rvListFollow.visibility = invisible
        }
    }

    override fun onFailed(message: String?) {
        followingFragmentBinding.apply {
            if (message == null) {
                tvMessage.text = resources.getString(R.string.no_following, username)
                tvMessage.visibility = visible
            } else {
                tvMessage.text = message
                tvMessage.visibility = visible
            }
            followProgressBar.visibility = invisible
            rvListFollow.visibility = invisible
        }
    }

    companion object {
        private const val KEY_BUNDLE = "USERNAME"

        fun getInstance(username: String): Fragment {
            return FollowingFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_BUNDLE, username)
                }
            }
        }
    }
}
