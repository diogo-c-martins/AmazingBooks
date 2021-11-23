package com.example.amazingbooksapp.repository

import com.example.amazingbooksapp.api.RetrofitInstance
import com.example.amazingbooksapp.db.ItemDatabase
import com.example.amazingbooksapp.models.Item


class BooksRepository(
    val db: ItemDatabase
) {
    suspend fun getBooks(searchString: String, startIndex: Int) =
        RetrofitInstance.api.getBooks(searchString,startIndex)

    suspend fun searchBooks(searchString: String,startIndex: Int) =
        RetrofitInstance.api.searchBooks(searchString,startIndex)

    suspend fun upsert(item: Item) = db.getItemDao().upsert(item)

    fun getSavedBooks() = db.getItemDao().getAllBooks()

    suspend fun deleteBook(item: Item) = db.getItemDao().deleteBook(item)
}