package com.dicoding.kaizer.githubuser.data.local

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoritUserDao {
    @Insert
    suspend fun addToFavorit(favoriteUser: FavoriteUser)

    @Query("SELECT * FROM favorite_user")
    fun getFavoritUser(): LiveData<List<FavoriteUser>>

    @Query("SELECT count(*) FROM favorite_user WHERE favorite_user.id = :id")
    suspend fun checkUser(id: Int): Int

    @Query("DELETE FROM favorite_user WHERE favorite_user.id = :id")
    suspend fun removeFromFavorite(id: Int): Int

    @Query("SELECT * FROM favorite_user")
    fun findAll(): Cursor

}