package com.example.rxjavausingretrofit2withmvvm.network.repository

import com.example.rxjavausingretrofit2withmvvm.network.BookListModel
import io.reactivex.Observable

interface BookRepository {
    fun getBookList(query: String): Observable<BookListModel>
}