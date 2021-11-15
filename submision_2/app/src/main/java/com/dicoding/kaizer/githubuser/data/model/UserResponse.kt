package com.dicoding.kaizer.githubuser.data.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName(value = "items")
    val items: ArrayList<Users>,

    val login: String,
    val id: Int,
    val followers_url: String,
    val following_url: String,
    val name: String,
    val location: String,
    val company: String,
    val following: Int,
    val followers: Int,
)

data class DetailUserResponse(
    val login: String,
    val id: Int,
    val followers_url: String,
    val following_url: String,
    val name: String,
    val repository: Int,
    val location: String,
    val company: String,
    val following: Int,
    val followers: Int,
    val avatar_url: String,

    val message: String
)

