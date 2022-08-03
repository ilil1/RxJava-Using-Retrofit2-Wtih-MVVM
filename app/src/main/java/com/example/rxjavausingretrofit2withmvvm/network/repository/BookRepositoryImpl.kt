package com.example.rxjavausingretrofit2withmvvm.network.repository

import com.example.rxjavausingretrofit2withmvvm.network.BookListModel
import com.example.rxjavausingretrofit2withmvvm.network.RetroService
import io.reactivex.Observable

class BookRepositoryImpl(
    private val retroService: RetroService
) : BookRepository {
    override fun getBookList(query: String): Observable<BookListModel> {
        return retroService.getBookListFromApi(query)
    }
}