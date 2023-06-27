package com.dewatwc.consumerfavorite.ui.favorite

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.dewatwc.consumerfavorite.R
import com.dewatwc.consumerfavorite.ui.favorite.DataBase.UserColumns.Companion.CONTENT_URI
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        getDataFavorite()
    }


    private fun showRecyclerView(cursor: Cursor?) {
        with(recycle_favorite) {
            layoutManager = LinearLayoutManager(context)
            adapter = FavoriteAdapter(MappingHelper.mapCursor(cursor))
        }
    }

    private fun setIllustration(state: Boolean) {
        not.isVisible = state
    }

    private fun getDataFavorite() {
        val cursor = contentResolver.query(CONTENT_URI, null, null, null, null)
        if (cursor != null) {
            if (cursor.count > 0) {
                setIllustration(false)
                showRecyclerView(cursor)
            } else {
                setIllustration(true)
                showRecyclerView(cursor)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.translate -> startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
        return super.onOptionsItemSelected(item)
    }
}