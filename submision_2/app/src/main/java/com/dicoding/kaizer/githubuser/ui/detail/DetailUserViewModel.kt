package com.dicoding.kaizer.githubuser.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.kaizer.githubuser.api.RetrofitClient
import com.dicoding.kaizer.githubuser.data.model.DetailUserResponse
import com.dicoding.kaizer.githubuser.data.model.Event
import com.dicoding.kaizer.githubuser.data.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel : ViewModel() {

    companion object {
        private const val TAG = "DetailViewModel"
    }

    val user = MutableLiveData<DetailUserResponse>()

    private val _toastText =  MutableLiveData<Event<String>>()
    val toasText: LiveData<Event<String>> = _toastText

    fun setUserDetail(username: String) {
        RetrofitClient.apiInstance
            .getUserDetail(username)
            .enqueue(object : Callback<DetailUserResponse>{
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

}