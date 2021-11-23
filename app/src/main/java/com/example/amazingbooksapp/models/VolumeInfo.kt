package com.example.amazingbooksapp.models

import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "volumeInfo"
)
@TypeConverters
data class VolumeInfo(
    @PrimaryKey()
    @ColumnInfo(name = "id")
    var id: String,
    @SerializedName("authors")
    val authors: List<String>,
    @SerializedName("categories")
    val categories: List<String>,
    @SerializedName("description")
    val description: String,
    @SerializedName("imageLinks")
    val imageLinks: ImageLinks,
    @SerializedName("infoLink")
    val infoLink: String,
    @SerializedName("previewLink")
    val previewLink: String,
    val canonicalVolumeLink: String?,
    @SerializedName("publishedDate")
    val publishedDate: String,
    @SerializedName("publisher")
    val publisher: String,
    @SerializedName("subtitle")
    val subtitle: String,
    @SerializedName("title")
    val title: String
)