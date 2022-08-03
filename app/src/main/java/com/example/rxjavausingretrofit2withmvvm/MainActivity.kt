package com.example.rxjavausingretrofit2withmvvm

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.example.rxjavausingretrofit2withmvvm.adapter.BookListAdapter
import com.example.rxjavausingretrofit2withmvvm.databinding.ActivityMainBinding
import com.example.rxjavausingretrofit2withmvvm.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {
    private val compositionRoot get() = (application as MyApplication).appCompositionRoot

    private lateinit var binding: ActivityMainBinding
    private val viewModel by lazy {
        ViewModelProvider(this, compositionRoot.viewModelFactory)[MainViewModel::class.java]
    }
    private val bookListAdapter by lazy {
        BookListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startObserving()
        initSearchBox()
        initRecyclerView()
    }

    private fun startObserving() {
        viewModel.bookList.observe(this) {
            if (it != null) {
                // update adapter
                bookListAdapter.bookListData = it.items
                bookListAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initSearchBox() {
        binding.inputBookName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.getBookList(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            adapter = bookListAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(
                DividerItemDecoration(this@MainActivity, VERTICAL)
            )
        }
    }
}