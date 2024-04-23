package com.hfad.bookshelf

import android.app.Application
import com.hfad.bookshelf.data.AppContainer
import com.hfad.bookshelf.data.DefaultAppContainer

class BookshelfApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}