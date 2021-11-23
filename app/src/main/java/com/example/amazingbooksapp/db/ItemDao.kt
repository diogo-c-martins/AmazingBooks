package com.example.amazingbooksapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.amazingbooksapp.models.Item

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: Item): Long


    @Query("SELECT * FROM items")
    fun getAllBooks(): LiveData<List<Item>>

    @Delete
    suspend fun deleteBook(item: Item)
}