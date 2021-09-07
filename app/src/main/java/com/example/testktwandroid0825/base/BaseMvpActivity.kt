package com.example.testktwandroid0825.base

import android.util.Log
import com.example.testktwandroid0825.ext.showToast

/**
 * created by echo
 * on 2021/8/30
 *
 */
@Suppress("UNCHECKED_CAST")
abstract class BaseMvpActivity<in V : IView, P : IPresenter<V>> : BaseActivity(), IView {

    protected var presenter: P? = null

    protected abstract fun createPresenter(): P

    override fun initView() {
        presenter = createPresenter()
        presenter?.attachView(this as V)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.detachView()
        this.presenter = null
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showMsg(msg: String) {
        showToast(msg)
    }

    override fun showDefaultMsg(msg: String) {
        showToast(msg)
    }

    override fun showError(errorMsg: String) {
        showToast(errorMsg)
    }
}