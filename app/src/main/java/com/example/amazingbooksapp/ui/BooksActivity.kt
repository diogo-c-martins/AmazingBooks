package com.example.amazingbooksapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.amazingbooksapp.R
import com.example.amazingbooksapp.db.ItemDatabase
import com.example.amazingbooksapp.repository.BooksRepository
import kotlinx.android.synthetic.main.activity_books.*


class BooksActivity : AppCompatActivity() {

    lateinit var viewModel: BooksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val booksRepository = BooksRepository(ItemDatabase(this))
        val viewModelProviderFactory = BooksViewModelProviderFactory(booksRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(BooksViewModel::class.java)
        setContentView(R.layout.activity_books)
        bottomNavigationView.setupWithNavController(booksNavHostFragment.findNavController())

    }
}
