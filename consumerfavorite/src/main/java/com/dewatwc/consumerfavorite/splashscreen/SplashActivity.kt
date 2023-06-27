package com.dewatwc.consumerfavorite.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dewatwc.consumerfavorite.R
import com.dewatwc.consumerfavorite.ui.favorite.FavoriteActivity

class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, FavoriteActivity::class.java))
            finish()
        }, 2000)

        val img: ImageView = findViewById(R.id.imageGitHub)
        Glide.with(this)
            .load(R.drawable.github)
            .apply(RequestOptions().override(500, 500))
            .into(img)

    }
}
