package com.dewatwc.githubuser.widget

import android.database.Cursor
import com.dewatwc.githubuser.data.DatUser
import com.dewatwc.githubuser.widget.DataBase.UserColumns.Companion.AVATAR
import com.dewatwc.githubuser.widget.DataBase.UserColumns.Companion.COMPANY
import com.dewatwc.githubuser.widget.DataBase.UserColumns.Companion.FOLLOWERS
import com.dewatwc.githubuser.widget.DataBase.UserColumns.Companion.FOLLOWING
import com.dewatwc.githubuser.widget.DataBase.UserColumns.Companion.HTML_URL
import com.dewatwc.githubuser.widget.DataBase.UserColumns.Companion.LOCATION
import com.dewatwc.githubuser.widget.DataBase.UserColumns.Companion.NAME
import com.dewatwc.githubuser.widget.DataBase.UserColumns.Companion.REPOS
import com.dewatwc.githubuser.widget.DataBase.UserColumns.Companion.USER_NAME

object DataWidget {

    fun mapCursor(favoriteCursor: Cursor?): ArrayList<DatUser> {
        val favoriteList = ArrayList<DatUser>()
        favoriteCursor?.apply {
            while (moveToNext()) {
                favoriteList.add(
                    DatUser(
                        name = getString(getColumnIndexOrThrow(NAME)),
                        username = getString(getColumnIndexOrThrow(USER_NAME)),
                        location = getString(getColumnIndexOrThrow(LOCATION)),
                        company = getString(getColumnIndexOrThrow(COMPANY)),
                        followers = getString(getColumnIndexOrThrow(FOLLOWERS)),
                        following = getString(getColumnIndexOrThrow(FOLLOWING)),
                        repositories = getString(getColumnIndexOrThrow(REPOS)),
                        avatar = getString(getColumnIndexOrThrow(AVATAR)),
                        html_url = getString(getColumnIndexOrThrow(HTML_URL))
                    )
                )
            }
        }
        return favoriteList
    }
}