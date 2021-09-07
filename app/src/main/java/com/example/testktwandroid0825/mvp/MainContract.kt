package com.example.testktwandroid0825.mvp

import com.example.testktwandroid0825.base.IModel
import com.example.testktwandroid0825.base.IPresenter
import com.example.testktwandroid0825.base.IView

/**
 * created by echo
 * on 2021/8/30
 *
 */
interface MainContract {
    interface View : IView {

    }

    interface Presenter : IPresenter<View> {

    }

    interface Model:IModel{

    }
}