package com.example.amazingbooksapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.amazingbooksapp.repository.BooksRepository

class BooksViewModelProviderFactory(
    val booksRepository: BooksRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BooksViewModel(booksRepository) as T
    }
}