package com.dewatwc.githubuser.ui.detail

import `in`.abhisheksaxena.toaster.Toaster
import android.content.Intent
import android.media.SoundPool
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dewatwc.githubuser.view.ViewPagerDetailAdapter
import com.dewatwc.githubuser.R
import com.dewatwc.githubuser.data.DatUser
import com.dewatwc.githubuser.data.database.AppDataBase
import com.dewatwc.githubuser.ui.favorite.FavoriteActivity
import com.dewatwc.githubuser.ui.home.HomeActivity
import com.dewatwc.githubuser.ui.settings.SettingActivity
import com.dewatwc.githubuser.view.ViewModelUser
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    private lateinit var user: String
    private lateinit var viewModelU: ViewModelUser
    private var favorite: Boolean = false

    private lateinit var sp: SoundPool
    private var soundId: Int = 0
    private var spLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.title = "DetailUser"
        viewModelU = ViewModelProvider(this).get(ViewModelUser::class.java)

        dataIntent()
        getDetail()
        favorite()
        pagerAdapter()
        sound()

    }

    private fun sound(){
        sp = SoundPool.Builder()
            .setMaxStreams(10)
            .build()
        sp.setOnLoadCompleteListener { _, _, status ->
            if (status == 0) {
                spLoaded = true
            }
        }
        soundId = sp.load(this@DetailActivity, R.raw.klik, 1)
    }

    private fun pagerAdapter(){
        val viewPagerDetailAdapter = ViewPagerDetailAdapter(this, supportFragmentManager)
        view_pager.adapter = viewPagerDetailAdapter
        tabs.setupWithViewPager(view_pager)
        supportActionBar?.elevation = 0f
    }

    private fun getDetail() {
        viewModelU.getDetail(user, applicationContext)
        viewModelU.getUserDetail().observe(this, Observer { data ->
            setDataDetail(data)
            addOrDeleteFavorite(data)
        })
    }

    private fun setDataDetail(dataUser: DatUser) {
        name.text = dataUser.name
        username.text = dataUser.username
        company.text = dataUser.company
        location.text = dataUser.location
        repo.text = dataUser.repositories
        followerss.text = dataUser.followers
        followings.text = dataUser.following
        Glide.with(this)
            .load(dataUser.avatar)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_account))
            .into(avatars)
    }

    fun getFollow(): String = user

    private fun dataIntent() {
        user = intent.getStringExtra(EXTRA_DATA) as String
    }

    private fun favorite() {
        AppDataBase.getInstance(applicationContext).userDao().getDetail(user)
            .observe(this, Observer { UserData ->
                if (UserData.isNotEmpty() && UserData[0].username.isNotEmpty()) {
                    favorite = true
                    icon_favorite.setImageDrawable(ContextCompat.getDrawable(
                            this,
                            R.drawable.ic_star
                        )
                    )
                } else {
                    favorite = false
                    icon_favorite.setImageDrawable(
                        ContextCompat.getDrawable(
                            this,
                            R.drawable.ic_star_border
                        )
                    )
                }
            })
    }

    private fun addOrDeleteFavorite(dataUser: DatUser) {
        icon_favorite.setOnClickListener {
            if (!favorite) {
                CoroutineScope(Dispatchers.IO + Job()).launch {
                    AppDataBase.getInstance(application).userDao().insert(dataUser)
                }
                if (spLoaded) { sp.play(soundId, 1f, 1f, 0, 0, 2f) }
                Toaster.popSuccess(this@DetailActivity, resources.getString(R.string.toast_sav), Toast.LENGTH_SHORT).show()
            } else {
                CoroutineScope(Dispatchers.IO + Job()).launch {
                    AppDataBase.getInstance(application).userDao().delete(dataUser)
                }
                if (spLoaded) { sp.play(soundId, 1f, 1f, 0, 0, 2f) }
                Toaster.popWarning(this@DetailActivity, resources.getString(R.string.toast_del), Toast.LENGTH_SHORT).show()
            }
            favorite()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite -> {
                if (spLoaded) { sp.play(soundId, 1f, 1f, 0, 0, 2f) }
                startActivity(Intent(applicationContext, FavoriteActivity::class.java))
            }
            R.id.settings -> {
                if (spLoaded) { sp.play(soundId, 1f, 1f, 0, 0, 2f) }
                startActivity(Intent(applicationContext, SettingActivity::class.java))
            }
            R.id.home_det -> {
                if (spLoaded) { sp.play(soundId, 1f, 1f, 0, 0, 2f) }
                startActivity(Intent(applicationContext, HomeActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
