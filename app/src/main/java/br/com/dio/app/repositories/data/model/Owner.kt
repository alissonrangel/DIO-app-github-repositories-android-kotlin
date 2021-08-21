package br.com.dio.app.repositories.data.model

import com.google.gson.annotations.SerializedName

data class Owner (
    val name: String,
    @SerializedName("avatar_url")
    val avatarURL: String,
    @SerializedName("html_url")
    val htmlURL: String
)
