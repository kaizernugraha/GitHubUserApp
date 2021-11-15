package com.dicoding.kaizer.consumersap

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContact {
    const val AUTHORITY = "com.dicoding.kaizer.githubuser"
    const val SCHEME = "content"

    internal class FavoriteUserColumns: BaseColumns {
        companion object {
            const val TABLE_NAME = "favorite_user"
            const val ID = "id"
            const val USERNAME = "login"
            const val AVATAR_URL = "avatar_url"

            val CONTENT_URI = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}