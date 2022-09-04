package com.example.rxjavausingretrofit2withmvvm.di

import androidx.core.util.Supplier
import com.example.rxjavausingretrofit2withmvvm.network.RetroService
import com.example.rxjavausingretrofit2withmvvm.network.repository.BookRepositoryImpl
import com.example.rxjavausingretrofit2withmvvm.viewModel.MainViewModel
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class AppCompositionRoot {

    private val baseUrl = "https://www.googleapis.com/books/v1/" //volums?q=harry

    private val retroInstance by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private val retroService by lazy {
        retroInstance.create(RetroService::class.java)
    }

    private val bookRepository get() = BookRepositoryImpl(retroService)

    //굳이 Supplier을 쓸 필요가 있는지 의문
    private val viewModelSupplier
        get() = Supplier {
            MainViewModel(bookRepository)
        }

    val viewModelFactory get() = MainViewModel.Factory(viewModelSupplier)
}