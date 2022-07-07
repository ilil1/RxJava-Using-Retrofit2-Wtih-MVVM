package com.example.rxjavausingretrofit2withmvvm.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rxjavausingretrofit2withmvvm.network.BookListModel
import com.example.rxjavausingretrofit2withmvvm.network.RetroInstance
import com.example.rxjavausingretrofit2withmvvm.network.RetroService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel() {

    lateinit var bookList: MutableLiveData<BookListModel>


    init {
        bookList = MutableLiveData()
    }

    fun getBookListObserver() : MutableLiveData<BookListModel>{
        return bookList
    }

    fun makeApiCall(query: String) {

        val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)
        retroInstance.getBookListFromApi(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getBookListObserverRx())
    }

    private fun getBookListObserverRx() : Observer<BookListModel> {
        return object : Observer<BookListModel>{
            override fun onSubscribe(d: Disposable) {
               //start showing progress indicator
            }

            override fun onNext(t: BookListModel) {
                bookList.postValue(t)
            }

            override fun onError(e: Throwable) {
                bookList.postValue(null)
            }

            override fun onComplete() {
              //hide progress indicator
            }


        }
    }


}