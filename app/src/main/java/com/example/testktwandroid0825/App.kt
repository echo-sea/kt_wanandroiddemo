package com.example.testktwandroid0825

import android.app.Application
import android.content.Context
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import kotlin.properties.Delegates

/**
 * created by echo
 * on 2021/8/26
 *
 */
class App : Application() {

    private var refWatcher: RefWatcher?=null

    companion object {

        val TAG = "wan_android"

        var context: Context by Delegates.notNull()
            private set

        lateinit var instance: Application

        fun getRefWatcher(context: Context):RefWatcher?{
            val app=context.applicationContext as App
            return app.refWatcher
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        context=applicationContext
        refWatcher=setupLeakCanary()
    }

    private fun setupLeakCanary(): RefWatcher {
        return if (LeakCanary.isInAnalyzerProcess(this)) {
            RefWatcher.DISABLED
        } else LeakCanary.install(this)
    }

}