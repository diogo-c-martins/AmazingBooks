package com.example.amazingbooksapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.amazingbooksapp.models.BookResponse
import com.example.amazingbooksapp.models.Item
import com.example.amazingbooksapp.repository.BooksRepository
import com.example.amazingbooksapp.utils.Resource

import kotlinx.coroutines.launch
import retrofit2.Response

class BooksViewModel(
    val booksRepository: BooksRepository
) : ViewModel() {

    val books: MutableLiveData<Resource<BookResponse>> = MutableLiveData()
    var startIndexPage = 1
    var booksResponse: BookResponse? = null

    val searchBooks: MutableLiveData<Resource<BookResponse>> = MutableLiveData()
    var searchBooksPage = 1
    var searchBooksResponse: BookResponse? = null

    init {
        getBooks("Mobile")
    }

    //Coroutine(as long as viewModel)
    fun getBooks(searchString: String) = viewModelScope.launch {
        books.postValue(Resource.Loading())
        val response = booksRepository.getBooks(searchString,startIndexPage)
        books.postValue(handleBookResponse(response))
    }

    fun searchBooks(searchString: String) = viewModelScope.launch {
        searchBooks.postValue(Resource.Loading())
        val response = booksRepository.searchBooks(searchString, searchBooksPage)
        searchBooks.postValue(handleSearchBooksResponse(response))
    }


    private fun handleBookResponse(response: Response<BookResponse>) : Resource<BookResponse>{
        if(response.isSuccessful){
            response.body()?.let { resultResponse ->
                startIndexPage++
                if(booksResponse == null){
                    booksResponse = resultResponse
                }else{ //More than 1 page
                    val oldBooks = booksResponse?.items
                    val newBooks = resultResponse.items
                        oldBooks?.addAll(newBooks)

                }
                return Resource.Success(booksResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchBooksResponse(response: Response<BookResponse>) : Resource<BookResponse>{
        if(response.isSuccessful){
            response.body()?.let { resultResponse ->
                searchBooksPage++
                if(searchBooksResponse == null){
                    searchBooksResponse = resultResponse
                }else{ //More than 1 page
                    val oldBooks = searchBooksResponse?.items
                    val newBooks = resultResponse.items
                        oldBooks?.addAll(newBooks)

                }
                return Resource.Success(searchBooksResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun saveBook(item: Item) = viewModelScope.launch {
        booksRepository.upsert(item)
    }

    fun getSavedBooks() = booksRepository.getSavedBooks()

    fun deleteBook(item: Item) = viewModelScope.launch {
        booksRepository.deleteBook(item)
    }

}