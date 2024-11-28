package com.cheezycode.quickpagingdemo

import android.app.Activity
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.filter
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cheezycode.quickpagingdemo.paging.QuotePagingAdapter
import com.cheezycode.quickpagingdemo.viewmodels.QuoteViewModel
import com.google.gson.GsonBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var quoteViewModel: QuoteViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var editText: EditText
    lateinit var adapter: QuotePagingAdapter
    lateinit var activity: Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activity=this

        recyclerView = findViewById(R.id.quoteList)
        editText = findViewById(R.id.edText)

        quoteViewModel = ViewModelProvider(this)[QuoteViewModel::class.java]

        adapter = QuotePagingAdapter(this@MainActivity,activity.getSharedPreferences("Paging3",MODE_PRIVATE)) {
            Log.e("TAG", "onCreate:---> ${it?.title}", )

        }


        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
        quoteViewModel.list.observe(this, Observer {
            adapter.submitData(lifecycle, it)
        })


        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if(!s!!.isEmpty()) {
                        filterQuotes(s.toString())
                    }


            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
       editText.setOnEditorActionListener { v, actionId, event ->
           if (actionId == EditorInfo.IME_ACTION_GO) {
               val query = editText.text.toString().trim()
               filterQuotes(query) // Perform the search
               true
           } else {
               false
           }
       }
    }

    private fun filterQuotes(query: String) {
        quoteViewModel.list.observe(this) { pagingData ->
            val filteredData = pagingData.map { it } // Keep original data
                .filter { it.title.contains(query) }
            adapter.submitData(lifecycle, filteredData)
        }
    }
}