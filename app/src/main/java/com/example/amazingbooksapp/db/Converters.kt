package com.example.amazingbooksapp.db

import androidx.room.TypeConverter
import com.example.amazingbooksapp.models.ImageLinks
import com.example.amazingbooksapp.models.VolumeInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
   @TypeConverter
    fun fromImageLink(imageLinks: ImageLinks): String?{
        return imageLinks.thumbnail
    }

    @TypeConverter
    fun toImageLink(thumbnail: String): ImageLinks {
        return ImageLinks(thumbnail)
    }

    @TypeConverter
    fun fromVolumeInfo(volumeInfo: VolumeInfo):String?{
        val gson = Gson()
        val type = object : TypeToken<VolumeInfo>(){

        }.type
        return gson.toJson(volumeInfo,type)
    }

    @TypeConverter
    fun toVolumeInfo ( volumeInfoString: String):VolumeInfo?{
        val gson = Gson()
        val type = object :TypeToken<VolumeInfo>(){

        }.type
        return  gson.fromJson(volumeInfoString,type)
    }

    @TypeConverter
    fun StringToList(authors: String?): List<String> {
        val gson = Gson()
        if (authors == null) {
            return emptyList()
        }

        val listType = object : TypeToken<List<String>>() {

        }.type

        return gson.fromJson(authors, listType)
    }

    @TypeConverter
    fun ListToString(authors: List<String>): String {
        val gson = Gson()
        return gson.toJson(authors)
    }
}