package com.dicoding.kaizer.githubuser.data.model

import com.google.gson.annotations.SerializedName
data class Users(

    @field:SerializedName("login")
    var login: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("avatar_url")
    var avatar_url: String? = null,

    @field:SerializedName("company")
    var company: String? = null,

    @field:SerializedName("location")
    var location: String? = null,

    @field:SerializedName("repos_url")
    var repos_url: String? = null,



)