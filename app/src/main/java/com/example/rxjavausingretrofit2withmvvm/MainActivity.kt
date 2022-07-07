package com.example.rxjavausingretrofit2withmvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.example.rxjavausingretrofit2withmvvm.adapter.BookListAdapter
import com.example.rxjavausingretrofit2withmvvm.databinding.ActivityMainBinding
import com.example.rxjavausingretrofit2withmvvm.network.BookListModel
import com.example.rxjavausingretrofit2withmvvm.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    lateinit var viewModel : MainViewModel
    lateinit var bookListAdapter : BookListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSearchBox()
        initRecyclerView()
    }

    fun initSearchBox(){
        binding.inputBookName.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loadAPIData(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }


    private fun initRecyclerView(){
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            val decoration  = DividerItemDecoration(applicationContext, VERTICAL)
            addItemDecoration(decoration)
            bookListAdapter = BookListAdapter()
            adapter =bookListAdapter
        }
    }

    fun loadAPIData(input: String){
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getBookListObserver().observe(this, Observer<BookListModel>{
            if (it != null){
                // update adapter
                bookListAdapter.bookListData = it.items
                bookListAdapter.notifyDataSetChanged()
            } else{
                Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.makeApiCall(input)
    }
}