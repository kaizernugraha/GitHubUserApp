package com.dicoding.kaizer.githubuser.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dicoding.kaizer.githubuser.data.local.DatabaseUser
import com.dicoding.kaizer.githubuser.data.local.FavoritUserDao
import com.dicoding.kaizer.githubuser.data.local.FavoriteUser

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private var userDao: FavoritUserDao?
    private var userDb: DatabaseUser? = DatabaseUser.getDatabase(application)

    init {
        userDao = userDb?.favoritUserDao()
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUser>>? {
        return userDao?.getFavoritUser()
    }
}