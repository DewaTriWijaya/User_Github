package com.dewatwc.githubuser.ui.follow.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dewatwc.githubuser.R
import com.dewatwc.githubuser.data.DataUsers
import com.dewatwc.githubuser.ui.detail.DetailActivity
import com.dewatwc.githubuser.ui.follow.FollowAdapter
import com.dewatwc.githubuser.view.ViewModelUser
import kotlinx.android.synthetic.main.fragment_following.*

class FollowingFragment : Fragment() {

    private lateinit var viewModel: ViewModelUser
    private var listFollowers = arrayListOf<DataUsers>()
    private var listData: ArrayList<DataUsers> = ArrayList()

    private val mainAdapter: FollowAdapter by lazy {
        FollowAdapter(listData)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shimmerFrameLayoutG.visibility = View.VISIBLE
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(ViewModelUser::class.java)
        setDataFollowing()
        showRecyclerList()
    }

    private fun setDataFollowing() {
        val activity: DetailActivity = activity as DetailActivity
        shimmerFrameLayoutG.visibility = View.VISIBLE
        mainAdapter.clearItems()
        viewModel.setFollowing(activity.getFollow(), requireContext())
        setViewModel()
    }

    private fun setViewModel() {
        viewModel.getUserData().observe(viewLifecycleOwner, Observer { UserData ->
            if (UserData.isNotEmpty()) {
                shimmerFrameLayoutG.visibility = View.VISIBLE
                listData = UserData
            }
            recycleViewFollowing.adapter = FollowAdapter(listData)
            shimmerFrameLayoutG.visibility = View.GONE
        })
    }

    private fun showRecyclerList() {
        shimmerFrameLayoutG.visibility = View.VISIBLE
        with(recycleViewFollowing){
            layoutManager = LinearLayoutManager(activity)
            adapter = FollowAdapter(listFollowers)
        }
    }
}
