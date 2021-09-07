package com.example.testktwandroid0825.base

/**
 * created by echo
 * on 2021/8/30
 *
 */
interface IPresenter<in V:IView> {
    /**
     * 绑定 View
     */
    fun attachView(mView:V)

    /**
     * 解绑 View
     */
    fun detachView()
}