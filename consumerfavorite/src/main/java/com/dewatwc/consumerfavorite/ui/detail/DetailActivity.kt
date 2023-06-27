package com.dewatwc.consumerfavorite.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dewatwc.consumerfavorite.R
import com.dewatwc.consumerfavorite.data.DataUser
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
    private lateinit var user: String
    private lateinit var viewModelU: ViewModelDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        viewModelU = ViewModelProvider(this).get(ViewModelDetail::class.java)
        dataIntent()
        setDataDetail()
        pagerAdapter()

    }

    private fun pagerAdapter(){
        val viewPagerDetailAdapter = ViewPagerDetailAdapter(this, supportFragmentManager)
        view_pager.adapter = viewPagerDetailAdapter
        tabs.setupWithViewPager(view_pager)
        supportActionBar?.elevation = 0f
    }

    private fun setDataDetail() {
        viewModelU.getDetail(user, applicationContext)
        viewModelU.getUserDetail().observe(this, Observer {data ->
            getDataFavorite(data)
        })
    }

    fun getFollow(): String = user

    private fun dataIntent(){
        user = intent.getStringExtra(EXTRA_DATA) as String
    }

    private fun getDataFavorite( dataUser: DataUser) {
        name.text = dataUser.name
        username.text = dataUser.username
        company.text = dataUser.company
        location.text = dataUser.location
        repo.text = dataUser.repository
        followerss.text = dataUser.followers
        followings.text = dataUser.following
        Glide.with(this)
            .load(dataUser.avatar)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_account))
            .into(avatars)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.translate -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}