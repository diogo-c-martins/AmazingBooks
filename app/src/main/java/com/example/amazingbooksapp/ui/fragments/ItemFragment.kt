package com.example.amazingbooksapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.amazingbooksapp.R
import com.example.amazingbooksapp.ui.BooksActivity
import com.example.amazingbooksapp.ui.BooksViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_item.*
import android.content.Intent
import android.net.Uri


class ItemFragment : Fragment(R.layout.fragment_item) {

    lateinit var viewModel: BooksViewModel

    val args: ItemFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as BooksActivity).viewModel
        val item = args.item
/*        webView.apply {
            webViewClient = WebViewClient()
            item.volumeInfo.canonicalVolumeLink?.let { loadUrl(it) }
        }*/

 //      Glide.with(this).load(item.volumeInfo.imageLinks.thumbnail).into(ivBookImageDetail)
        tvTitleDetail.text = item.volumeInfo.title
        tvDescriptionDetail.text = item.volumeInfo.description
        tvSubtitleDetail.text = item.volumeInfo.subtitle
        tvBuyLink.setOnClickListener { res ->
            try {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(item.SaleInfo?.buyLink))
                startActivity(browserIntent)
            }catch (e: NullPointerException){
                Snackbar.make(view, "No Buy Link available", Snackbar.LENGTH_SHORT).apply {
                    show()
                    var nul = item.SaleInfo?.buyLink
                    nul = ""
                }
            }
        }


           fab2.setOnClickListener {
                viewModel.saveBook(item)
                Snackbar.make(view, "Book Saved!", Snackbar.LENGTH_SHORT).show()
            }
        }
    }