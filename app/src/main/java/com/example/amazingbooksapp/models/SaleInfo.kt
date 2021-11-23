package com.example.amazingbooksapp.models

import androidx.room.Entity
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
@Entity(
    tableName = "saleInfo"
)
@TypeConverters
data class SaleInfo(
    @SerializedName("buyLink")
    val buyLink: String?
)