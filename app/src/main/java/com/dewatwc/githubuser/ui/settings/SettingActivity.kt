package com.dewatwc.githubuser.ui.settings

import android.content.Intent
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.dewatwc.githubuser.R
import com.dewatwc.githubuser.ui.favorite.FavoriteActivity
import com.dewatwc.githubuser.ui.home.HomeActivity

class SettingActivity : AppCompatActivity() {

    private lateinit var sp: SoundPool
    private var soundId: Int = 0
    private var spLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        supportActionBar?.title = getString(R.string.setting)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settingsS, SettingFragment())
            .commit()

        sound()
    }

    private fun sound(){
        sp = SoundPool.Builder()
            .setMaxStreams(10)
            .build()
        sp.setOnLoadCompleteListener { _, _, status ->
            if (status == 0) { spLoaded = true } }
        soundId = sp.load(this@SettingActivity, R.raw.klik, 1)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_set, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.favorite_set -> {
                if (spLoaded) { sp.play(soundId, 1f, 1f, 0, 0, 2f) }
                startActivity(Intent(applicationContext, FavoriteActivity::class.java))
            }
            R.id.home_set -> {
                if (spLoaded) { sp.play(soundId, 1f, 1f, 0, 0, 2f) }
                startActivity(Intent(applicationContext, HomeActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}