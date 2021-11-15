package com.dicoding.kaizer.githubuser

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Users (
        var name: String,
        var username: String,
        var location: String,
        var company: String,
        var repo: String,
        var followers: String,
        var following: String,
        var photo: Int
) : Parcelable