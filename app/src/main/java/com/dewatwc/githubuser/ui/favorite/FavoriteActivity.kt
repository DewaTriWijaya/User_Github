package com.dewatwc.githubuser.ui.favorite

import android.content.Intent
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dewatwc.githubuser.R
import com.dewatwc.githubuser.data.DatUser
import com.dewatwc.githubuser.data.database.AppDataBase
import com.dewatwc.githubuser.ui.home.HomeActivity
import com.dewatwc.githubuser.ui.settings.SettingActivity
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity() {

    private lateinit var sp: SoundPool
    private var soundId: Int = 0
    private var spLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        supportActionBar?.title = "FavoriteUser"
        fav_not.isVisible = false
        getFavoriteData()
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
        soundId = sp.load(this@FavoriteActivity, R.raw.klik, 1)
    }

    private fun showRecyclerList(UserData: List<DatUser>) {
       with(recycleViewFavorite){
           layoutManager = LinearLayoutManager(context)
           adapter = FavoriteAdapter(UserData)
       }
    }

    private fun getFavoriteData() {
        AppDataBase.getInstance(applicationContext).userDao().getUsers().observe(this, Observer { userData ->
            if (userData.isNotEmpty()) {
                fav_not.isVisible = false
                showRecyclerList(userData)
            } else {
                fav_not.isVisible = true
                showRecyclerList(userData)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favo, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home_fav -> {
                if (spLoaded) { sp.play(soundId, 1f, 1f, 0, 0, 2f) }
                startActivity(Intent(applicationContext, HomeActivity::class.java))
            }
            R.id.settings_fav -> {
                if (spLoaded) { sp.play(soundId, 1f, 1f, 0, 0, 2f) }
                startActivity(Intent(applicationContext, SettingActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
