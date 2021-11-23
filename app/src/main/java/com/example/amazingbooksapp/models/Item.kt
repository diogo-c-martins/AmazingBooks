package com.example.amazingbooksapp.models


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(
    tableName = "items"
)
@TypeConverters
data class Item(
    @PrimaryKey()
    @SerializedName("id")
    val id: String,
    @SerializedName("selfLink")
    val selfLink: String,
    @SerializedName("volumeInfo")
    val volumeInfo: VolumeInfo,
    @SerializedName("saleInfo")
    val SaleInfo: SaleInfo?
) : Serializable