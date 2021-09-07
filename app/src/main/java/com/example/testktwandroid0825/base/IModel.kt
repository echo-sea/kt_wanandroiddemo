package com.example.testktwandroid0825.base

import io.reactivex.disposables.Disposable

/**
 * created by echo
 * on 2021/8/30
 *
 */
interface IModel {
    fun addDisposable(disposable: Disposable?)
    fun onDetach()
}