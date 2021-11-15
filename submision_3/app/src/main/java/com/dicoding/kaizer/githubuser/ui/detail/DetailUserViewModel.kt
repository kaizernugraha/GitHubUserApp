package com.dicoding.kaizer.githubuser.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.kaizer.githubuser.api.RetrofitClient
import com.dicoding.kaizer.githubuser.data.local.DatabaseUser
import com.dicoding.kaizer.githubuser.data.local.FavoritUserDao
import com.dicoding.kaizer.githubuser.data.local.FavoriteUser
import com.dicoding.kaizer.githubuser.data.model.DetailUserResponse
import com.dicoding.kaizer.githubuser.data.model.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private const val TAG = "DetailViewModel"
    }

    private var userDao: FavoritUserDao?
    private var userDb: DatabaseUser? = DatabaseUser.getDatabase(application)

    init {
        userDao = userDb?.favoritUserDao()
    }

    val user = MutableLiveData<DetailUserResponse>()

    private val _toastText = MutableLiveData<Event<String>>()
    val toasText: LiveData<Event<String>> = _toastText

    fun setUserDetail(username: String) {
        RetrofitClient.apiInstance
            .getUserDetail(username)
            .enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                        _toastText.value = Event(response.body()?.message.toString())
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message}")
                }

            })
    }

    fun getUserDetail(): LiveData<DetailUserResponse> {
        return user
    }

    fun addToFavorite(username: String, id: Int, avatarUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            var user = FavoriteUser(
                username,
                id,
                avatarUrl
            )
            userDao?.addToFavorit(user)
        }
    }

    suspend fun checkUser(id: Int) = userDao?.checkUser(id)

    fun removeFromFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromFavorite(id)
        }
    }
}