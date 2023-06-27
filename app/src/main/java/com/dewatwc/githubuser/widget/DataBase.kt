package com.dewatwc.githubuser.widget

import android.net.Uri
import android.provider.BaseColumns

object DataBase {
    private const val AUTHORITY = "com.dewatwc.githubuser"
    private const val SCHEME = "content"

    class UserColumns : BaseColumns {
        companion object {
            private const val TABLE_NAME = "data_user"
            const val NAME = "name"
            const val USER_NAME = "username"
            const val LOCATION = "location"
            const val COMPANY = "company"
            const val FOLLOWERS = "followers"
            const val FOLLOWING = "following"
            const val REPOS = "repositories"
            const val AVATAR = "avatar"
            const val HTML_URL = "html_url"

            val CONTENT_URI: Uri = Uri.Builder()
                .scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}