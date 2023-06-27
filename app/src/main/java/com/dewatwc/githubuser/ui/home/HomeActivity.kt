package com.dewatwc.githubuser.ui.home

import android.content.Intent
import android.media.SoundPool
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dewatwc.githubuser.R
import com.dewatwc.githubuser.data.DataUsers
import com.dewatwc.githubuser.ui.favorite.FavoriteActivity
import com.dewatwc.githubuser.ui.settings.SettingActivity
import com.dewatwc.githubuser.view.ViewModelUser
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var mainViewModel: ViewModelUser
    private var listData: ArrayList<DataUsers> = ArrayList()

    private lateinit var sp: SoundPool
    private var soundId: Int = 0
    private var spLoaded = false

    private val mainAdapter: HomeAdapter by lazy {
        HomeAdapter(listData)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportActionBar?.title = "GitHubUser"
        progress.visibility = View.GONE
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(ViewModelUser::class.java)

        recyclerView()
        searchData()
        getDataHome()
        sound()
    }

    private fun recyclerView(){
        recycleView.layoutManager = LinearLayoutManager(recycleView.context)
        recycleView.setHasFixedSize(true)
        recycleView.addItemDecoration(DividerItemDecoration(recycleView.context, DividerItemDecoration.VERTICAL))
    }

    private fun sound(){
        sp = SoundPool.Builder()
            .setMaxStreams(10)
            .build()
        sp.setOnLoadCompleteListener { _, _, status ->
            if (status == 0) { spLoaded = true } }
        soundId = sp.load(this@HomeActivity, R.raw.klik, 1)
    }

    private fun searchData() {
        user_search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isEmpty()) {
                    return true
                } else {
                    mainAdapter.clearItems()
                    mainViewModel.getDataSearch(query, applicationContext)
                    getDataHome()
                    not.isVisible = false
                }
                progress.visibility = View.VISIBLE
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }


    private fun getDataHome() {
        mainViewModel.getUserData().observe(this@HomeActivity, Observer { userData ->
            progress.visibility = View.VISIBLE
            if (userData != null) {
                listData = userData
                not.isVisible = false
                showRecyclerList()
                progress.visibility = View.GONE
            } else {
                listData = userData
                not.isVisible = true
                showRecyclerList()
            }
        })
    }

    private fun showRecyclerList() {
        with(recycleView){
            layoutManager = LinearLayoutManager(context)
            adapter = HomeAdapter(listData)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite_home -> {
                if (spLoaded) { sp.play(soundId, 1f, 1f, 0, 0, 2f) }
                startActivity(Intent(applicationContext, FavoriteActivity::class.java))
            }
            R.id.settings_home -> {
                if (spLoaded) { sp.play(soundId, 1f, 1f, 0, 0, 2f) }
                startActivity(Intent(applicationContext, SettingActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}