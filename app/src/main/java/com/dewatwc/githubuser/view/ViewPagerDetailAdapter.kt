package com.dewatwc.githubuser.view

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dewatwc.githubuser.R
import com.dewatwc.githubuser.ui.follow.followers.FollowersFragment
import com.dewatwc.githubuser.ui.follow.following.FollowingFragment

class ViewPagerDetailAdapter(private val mContext: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val tab = listOf(
        FollowersFragment(),
        FollowingFragment()
    )
    
    private val tabTitle = intArrayOf(R.string.followers, R.string.following)

    override fun getItem(position: Int): Fragment = tab[position]


    override fun getCount(): Int = tab.size


    override fun getPageTitle(position: Int): CharSequence? =
        mContext.resources.getString(tabTitle[position])

}