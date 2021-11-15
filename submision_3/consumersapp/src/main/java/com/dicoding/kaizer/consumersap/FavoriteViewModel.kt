package com.dicoding.kaizer.consumersap

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {


    private var list = MutableLiveData<ArrayList<Users>>()


    fun setFavoriteUser(context: Context) {
        val cursor = context.contentResolver.query(
            DatabaseContact.FavoriteUserColumns.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        //converte
        val listConverted = MappingHelper.mapCursorToArrayList(cursor)
        list.postValue(listConverted)
    }

    fun getFavoriteUser(): LiveData<ArrayList<Users>>? {
        return list
    }
}