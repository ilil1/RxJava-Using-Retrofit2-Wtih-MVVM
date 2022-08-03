package com.example.rxjavausingretrofit2withmvvm.viewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.util.Supplier
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rxjavausingretrofit2withmvvm.network.BookListModel
import com.example.rxjavausingretrofit2withmvvm.network.repository.BookRepository
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(
    private val repository: BookRepository
) : ViewModel() {
    private val _bookList: MutableLiveData<BookListModel> = MutableLiveData()
    val bookList: LiveData<BookListModel> get() = _bookList

    fun getBookList(query: String) {
        repository.getBookList(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getBookListObserverRx())
    }

    private fun getBookListObserverRx(): Observer<BookListModel> {
        return object : Observer<BookListModel> {
            override fun onSubscribe(d: Disposable) {
                //start showing progress indicator
            }

            override fun onNext(t: BookListModel) {
                _bookList.value = t
            }

            override fun onError(e: Throwable) {
                _bookList.value = null
            }

            override fun onComplete() {
                //hide progress indicator
            }
        }
    }

    class Factory(
        private val viewModelSupplier: Supplier<MainViewModel>
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        @RequiresApi(Build.VERSION_CODES.N)
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return viewModelSupplier.get() as T
        }
    }
}