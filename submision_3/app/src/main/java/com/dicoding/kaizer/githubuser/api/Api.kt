package com.dicoding.kaizer.githubuser.api

import com.dicoding.kaizer.githubuser.data.model.DetailUserResponse
import com.dicoding.kaizer.githubuser.data.model.UserResponse
import com.dicoding.kaizer.githubuser.data.model.Users
import retrofit2.Call
import retrofit2.http.*

interface Api {
    @GET("search/users")

    @Headers("Authorization: token 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")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path(value = "username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path(value = "username") username: String
    ): Call<ArrayList<Users>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path(value = "username") username: String
    ): Call<ArrayList<Users>>
}