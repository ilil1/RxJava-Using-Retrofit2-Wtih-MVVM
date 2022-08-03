package com.example.rxjavausingretrofit2withmvvm

import android.app.Application
import com.example.rxjavausingretrofit2withmvvm.di.AppCompositionRoot

class MyApplication : Application() {
    val appCompositionRoot by lazy {
        AppCompositionRoot()
    }
}