package com.practice.kotlinwork.presentation.search

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.practice.kotlinwork.R

import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.content_search.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.Serializable

class SearchActivity : AppCompatActivity() {

    val searchViewModel : SearchViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            val tag = edit_search_text.text
            searchViewModel.getSearchPhotos(tag.toString()).observe(this,
                    Observer {
                        it.toString()
                        val intent = Intent()
                        intent.putExtra("CompleteObject", it as Serializable)
                        setResult(101, intent)
                        finish()
                    })


//            Snackbar.make(view, "Some Smart action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
        }
    }

}
