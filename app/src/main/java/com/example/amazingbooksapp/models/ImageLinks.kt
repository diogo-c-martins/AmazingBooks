package com.example.amazingbooksapp.models


import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName

@TypeConverters
data class ImageLinks(
    @SerializedName("thumbnail")
    val thumbnail: String
)