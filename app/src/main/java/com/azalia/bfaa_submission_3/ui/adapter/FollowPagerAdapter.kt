package com.azalia.bfaa_submission_3.ui.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.azalia.bfaa_submission_3.ui.follower.FollowerFragment
import com.azalia.bfaa_submission_3.ui.following.FollowingFragment
import com.azalia.bfaa_submission_3.util.Constanta.TAB_TITLES

class FollowPagerAdapter(activity: AppCompatActivity, private val username: String) : FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowingFragment.getInstance(username)
            1 -> fragment = FollowerFragment.getInstance(username)
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return TAB_TITLES.size
    }
}