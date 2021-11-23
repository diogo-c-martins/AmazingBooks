package com.example.amazingbooksapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.amazingbooksapp.R
import com.example.amazingbooksapp.adapters.BooksAdapter
import com.example.amazingbooksapp.ui.BooksActivity
import com.example.amazingbooksapp.ui.BooksViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_saved_books.*

class SavedBooksFragment : Fragment(R.layout.fragment_saved_books) {

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
                R.id.action_savedBooksFragment_to_ItemFragment,
                bundle
            )
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val item = booksAdapter.differ.currentList[position]
                viewModel.deleteBook(item)
                Snackbar.make(view, "Deleted book", Snackbar.LENGTH_LONG).apply {
                     setAction("Undo"){
                         viewModel.saveBook(item)
                     }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(rvSavedBooks)
        }

        viewModel.getSavedBooks().observe(viewLifecycleOwner, Observer { items ->
            booksAdapter.differ.submitList(items)
        })
    }

    private fun setupRecyclerView(){
        booksAdapter = BooksAdapter()
        rvSavedBooks.apply {
            adapter = booksAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}