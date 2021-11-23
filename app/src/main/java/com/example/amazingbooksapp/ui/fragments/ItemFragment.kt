package com.example.amazingbooksapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.example.amazingbooksapp.R
import com.example.amazingbooksapp.ui.BooksActivity
import com.example.amazingbooksapp.ui.BooksViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_item.*


class ItemFragment : Fragment(R.layout.fragment_item) {

    lateinit var viewModel: BooksViewModel

    val args: ItemFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as BooksActivity).viewModel
        val item = args.item
        webView.apply {
            webViewClient = WebViewClient()
            item.volumeInfo.canonicalVolumeLink?.let { loadUrl(it) }
        }

        fab.setOnClickListener {
            viewModel.saveBook(item)
            Snackbar.make(view, "Book Saved!", Snackbar.LENGTH_SHORT).show()
        }

    }
}