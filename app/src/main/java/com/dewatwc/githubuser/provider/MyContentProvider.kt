package com.dewatwc.githubuser.provider

import android.content.*
import android.database.Cursor
import android.net.Uri
import com.dewatwc.githubuser.data.database.AppDataBase
import com.dewatwc.githubuser.data.database.UserDao
import com.dewatwc.githubuser.widget.Widget
import java.lang.UnsupportedOperationException

class MyContentProvider : ContentProvider() {

    private lateinit var userDao: UserDao

    companion object {
        private const val USER_FAVORITE = 1
        private const val AUTHORITY = "com.dewatwc.githubuser"
        private const val TABLE_USER = "data_user"
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(AUTHORITY, TABLE_USER, USER_FAVORITE)
        }
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when (uriMatcher.match(uri)){
            USER_FAVORITE -> userDao.cursorUser()
            else -> null
        }
    }

    override fun onCreate(): Boolean {
        userDao = AppDataBase.getInstance(context as Context).userDao()
        return true
    }


    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        refreshWidget()
        throw UnsupportedOperationException()
    }


    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        refreshWidget()
        throw UnsupportedOperationException()
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    private fun refreshWidget() {
        Widget.refreshBroadcast(context as Context)
    }
}
