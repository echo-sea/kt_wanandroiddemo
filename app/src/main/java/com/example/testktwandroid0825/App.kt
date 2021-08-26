package com.example.testktwandroid0825

import android.app.Application
import android.content.Context
import kotlin.properties.Delegates

/**
 * created by echo
 * on 2021/8/26
 *
 */
class App : Application() {
    companion object {
        var context: Context by Delegates.notNull()
        lateinit var instance: Application
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}