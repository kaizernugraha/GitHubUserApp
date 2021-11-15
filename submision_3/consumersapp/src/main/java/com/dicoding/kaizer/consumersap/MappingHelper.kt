package com.dicoding.kaizer.consumersap

import android.database.Cursor


object MappingHelper {
    fun mapCursorToArrayList(cursor: Cursor?): ArrayList<Users>{
        val list = ArrayList<Users>()
        if (cursor != null) {
            while (cursor.moveToNext()){
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContact.FavoriteUserColumns.ID))
                val username = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContact.FavoriteUserColumns.USERNAME))
                val avatarUrl = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContact.FavoriteUserColumns.AVATAR_URL))
                list.add(
                    Users(
                        username,
                        id,
                        avatarUrl
                    )
                )
            }
        }
        return list
    }
}