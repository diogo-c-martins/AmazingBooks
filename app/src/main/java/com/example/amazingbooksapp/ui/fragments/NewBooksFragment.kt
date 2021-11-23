package com.example.amazingbooksapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.amazingbooksapp.R
import com.example.amazingbooksapp.adapters.BooksAdapter
import com.example.amazingbooksapp.ui.BooksActivity
import com.example.amazingbooksapp.ui.BooksViewModel
import com.example.amazingbooksapp.utils.Constants.Companion.QUERY_PAGE_SIZE
import com.example.amazingbooksapp.utils.Constants.Companion.TAG_BOOK
import com.example.amazingbooksapp.utils.Resource
import kotlinx.android.synthetic.main.fragment_new_books.*


class NewBooksFragment : Fragment(R.layout.fragment_new_books) {

    lateinit var viewModel: BooksViewModel
    lateinit var booksAdapter: BooksAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as BooksActivity).viewModel
       setupRecyclerView()

        booksAdapter.setOnItemClickListener {
            val bundle = Bundle().apply{
                putSerializable("item", it)
            }
            findNavController().navigate(
                R.id.action_NewBooksFragment_to_ItemFragment,
                bundle
            )
        }


        viewModel.books.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { bookResponse ->
                        booksAdapter.differ.submitList(bookResponse.items.toList())
                        val totalPages = bookResponse.totalItems / QUERY_PAGE_SIZE + 2
                        isLastPage = viewModel.startIndexPage == totalPages
                        if(isLastPage){
                            rvBooksInfo.setPadding(0,0,0,0)
                        }
                    }
                }

                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Log.e(TAG_BOOK, "An error occurred")
                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun setupRecyclerView(){
        booksAdapter = BooksAdapter()
        rvBooksInfo.apply {
            adapter = booksAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@NewBooksFragment.scrollListener)
        }
    }

    private fun hideProgressBar(){
        paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar(){
        paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val scrollListener = object : RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling
            if(shouldPaginate) {
                viewModel.getBooks("Mobile")
                isScrolling = false
            }
        }
    }

}