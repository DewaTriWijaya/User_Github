package com.dewatwc.githubuser.widget

import android.content.Context
import android.database.Cursor
import android.os.Binder
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dewatwc.githubuser.R
import com.dewatwc.githubuser.data.DatUser
import com.dewatwc.githubuser.data.database.AppDataBase
import com.dewatwc.githubuser.data.database.UserDao
import com.dewatwc.githubuser.widget.DataBase.UserColumns.Companion.CONTENT_URI

internal class WidgetRemote(private val context: Context) :
    RemoteViewsService.RemoteViewsFactory {

    private var list = ArrayList<DatUser>()
    private var cursor: Cursor? = null
    private lateinit var userDao: UserDao


    override fun onCreate() {
        userDao = AppDataBase.getInstance(context).userDao()
    }

    override fun onDataSetChanged() {
        val identityToken = Binder.clearCallingIdentity()
        val cursor = context.contentResolver?.query(
            CONTENT_URI,
            null,
            null,
            null,
            null
        )
        val item = DataWidget.mapCursor(cursor)
        list.clear()
        list = item

        Binder.restoreCallingIdentity(identityToken)
    }

    override fun onDestroy() {
        cursor?.close()
        list = arrayListOf()
    }

    override fun getCount(): Int = list.size

    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(position: Int): Long = 0

    override fun hasStableIds(): Boolean = false

    override fun getViewTypeCount(): Int = 1

    override fun getViewAt(position: Int): RemoteViews {

        val remoteViews = RemoteViews(context.packageName, R.layout.item_widget)

        return try {
            val bitmap = Glide.with(context)
                .asBitmap()
                .load(list[position].avatar)
                .apply(RequestOptions().centerCrop())
                .submit(800, 550)
                .get()
            remoteViews.setImageViewBitmap(R.id.avatarW, bitmap)
            remoteViews.setTextViewText(R.id.userW, list[position].username)
            remoteViews.setTextViewText(R.id.companyW, list[position].company)
            remoteViews
        } catch (e: Exception) {
            remoteViews
        }
    }
}