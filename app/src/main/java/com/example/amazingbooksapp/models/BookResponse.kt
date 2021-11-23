package com.example.amazingbooksapp.models


import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName

@TypeConverters
data class BookResponse(
    @SerializedName("items")
    val items: MutableList<Item>,
    val totalItems: Int
)