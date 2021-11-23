package com.example.amazingbooksapp.api


import com.example.amazingbooksapp.models.BookResponse
import com.example.amazingbooksapp.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BooksAPI {

    @GET("books/v1/volumes")
    suspend fun getBooks(
        @Query("q")
        searchString: String,
        @Query("startIndex")
        startIndex: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<BookResponse>

    @GET("books/v1/volumes")
    suspend fun searchBooks(
        @Query("q")
        searchString: String,
        @Query("startIndex")
        startIndex: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<BookResponse>
}