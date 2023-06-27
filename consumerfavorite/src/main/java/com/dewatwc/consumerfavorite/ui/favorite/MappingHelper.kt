package com.dewatwc.consumerfavorite.ui.favorite

import android.database.Cursor
import com.dewatwc.consumerfavorite.data.DataUser

object MappingHelper {

    fun mapCursor(cursor: Cursor?): ArrayList<DataUser> {
        val favoriteData = ArrayList<DataUser>()
        cursor?.apply {
            while (moveToNext()) {
                favoriteData.add(
                    DataUser(
                        name = getString(getColumnIndexOrThrow(DataBase.UserColumns.NAME)),
                        username = getString(getColumnIndexOrThrow(DataBase.UserColumns.USER_NAME)),
                        location = getString(getColumnIndexOrThrow(DataBase.UserColumns.LOCATION)),
                        company = getString(getColumnIndexOrThrow(DataBase.UserColumns.COMPANY)),
                        followers = getString(getColumnIndexOrThrow(DataBase.UserColumns.FOLLOWERS)),
                        following = getString(getColumnIndexOrThrow(DataBase.UserColumns.FOLLOWING)),
                        repository = getString(getColumnIndexOrThrow(DataBase.UserColumns.REPOS)),
                        avatar = getString(getColumnIndexOrThrow(DataBase.UserColumns.AVATAR)),
                        html_url = getString(getColumnIndexOrThrow(DataBase.UserColumns.HTML_URL))
                    )
                )
            }
        }
        return favoriteData
    }
}