package com.example.testktwandroid0825

import android.content.DialogInterface
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.FragmentTransaction
import com.cxz.wanandroid.utils.DialogUtil
import com.cxz.wanandroid.utils.Preference
import com.cxz.wanandroid.utils.SettingUtil
import com.example.testktwandroid0825.base.BaseMvpActivity
import com.example.testktwandroid0825.constant.Constant
import com.example.testktwandroid0825.event.ColorEvent
import com.example.testktwandroid0825.ext.showToast
import com.example.testktwandroid0825.fragment.*
import com.example.testktwandroid0825.mvp.MainContract
import com.example.testktwandroid0825.presenter.MainPresenter
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : BaseMvpActivity<MainContract.View, MainContract.Presenter>(),MainContract.View {

    private val BOTTOM_INDEX: String = "bottom_index"

    private val FRAGMENT_HOME = 0x01
    private val FRAGMENT_SQUARE = 0x02
    private val FRAGMENT_WECHAT = 0x03
    private val FRAGMENT_SYSTEM = 0x04
    private val FRAGMENT_PROJECT = 0x05

    private var mIndex=FRAGMENT_HOME

    /**
     * username TextView
     */
    private var nav_username: TextView? = null

    /**
     * user_id TextView
     */
    private var nav_user_id: TextView? = null

    /**
     * user_grade TextView
     */
    private var nav_user_grade: TextView? = null

    /**
     * user_rank TextView
     */
    private var nav_user_rank: TextView? = null

    /**
     * score TextView
     */
    private var nav_score: TextView? = null

    /**
     * rank ImageView
     */
    private var nav_rank: ImageView? = null

    /**
     * local username
     */
    private var username :String by Preference(Constant.USERNAME_KEY,"")

    private var mHomeFragment: HomeFragment? = null
    private var mSquareFragment: SquareFragment? = null
    private var mWeChatFragment: WeChatFragment? = null
    private var mSystemFragment: SystemFragment? = null
    private var mProjectFragment: ProjectFragment? = null


    override fun attachLayoutRes(): Int =R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState!=null){
            mIndex=savedInstanceState?.getInt(BOTTOM_INDEX)
        }
        super.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(BOTTOM_INDEX,mIndex)
    }

    override fun createPresenter(): MainContract.Presenter=MainPresenter()

    override fun start() {
        //???????????? todo
    }


    override fun initData() {
        //todo ??????bugly
    }

    override fun initColor() {
        super.initColor()
        refreshColor(ColorEvent(true))
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun refreshColor(event: ColorEvent){
        if (event.isRefresh){
            nav_view.getHeaderView(0).setBackgroundColor(mThemeColor)
            floating_action_btn.backgroundTintList= ColorStateList.valueOf(mThemeColor)
        }
    }
    override fun initView() {
        super.initView()
        toolbar.run {
            title=getString(R.string.app_name)
            setSupportActionBar(this)
        }

        bottom_navigation.run {
            // ???????????? BottomNavigationViewHelper.disableShiftMode(this) ??????????????????????????????????????????????????????????????????
            // ????????? 28.0.0 ???????????????????????? BottomNavigationView ????????????????????? labelVisibilityMode = 1 ?????????
            // BottomNavigationViewHelper.disableShiftMode(this)
            labelVisibilityMode= LabelVisibilityMode.LABEL_VISIBILITY_LABELED
            //todo ??????
            setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        }

        //??????
        initDrawerLayout()

        initNavView()

        //??????fragment
        showFragment(mIndex)

        //todo floating_action_btn
        floating_action_btn.run {
            setOnClickListener(onFABClickListener)
        }
    }

    private fun initDrawerLayout() {
        layout_drawer.run {
            val toggle=ActionBarDrawerToggle(
                this@MainActivity,
                this,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
            )
            addDrawerListener(toggle)
            toggle.syncState()
        }
    }

    private fun initNavView() {
        nav_view.run {
            setNavigationItemSelectedListener(onDrawerNavigationItemSelectedListener)
            nav_username=getHeaderView(0).findViewById(R.id.tv_username)
            nav_user_id = getHeaderView(0).findViewById(R.id.tv_user_id)
            nav_user_grade = getHeaderView(0).findViewById(R.id.tv_user_grade)
            nav_user_rank = getHeaderView(0).findViewById(R.id.tv_user_rank)
            nav_rank = getHeaderView(0).findViewById(R.id.iv_rank)
            nav_score=MenuItemCompat.getActionView(nav_view.menu.findItem(R.id.nav_score)) as TextView
            nav_score?.gravity=Gravity.CENTER_VERTICAL
            menu.findItem(R.id.nav_logout).isVisible=isLogin
        }
        nav_username?.run {
            text=if (!isLogin) resources.getString(R.string.go_login) else username
            setOnClickListener {
                if (!isLogin){
                    //TODO ????????????
                }
            }
        }
        nav_rank?.setOnClickListener {
            //TODO ??????????????????
        }
    }

    //??????????????????
    private val onDrawerNavigationItemSelectedListener= NavigationView.OnNavigationItemSelectedListener {item->
            when(item.itemId){
                //??????
                R.id.nav_score ->{
                    if (isLogin){
                        // todo ??????????????????

                    }else{
                        showToast(resources.getString(R.string.login_tint))
                        goLogin()
                    }
                }
                //??????
                R.id.nav_collect -> {
                    if (isLogin) {
                        //todo
//                        goCommonActivity(Constant.Type.COLLECT_TYPE_KEY)
                    } else {
                        showToast(resources.getString(R.string.login_tint))
                        goLogin()
                    }
                }
                //??????
                R.id.nav_share -> {
                    if (isLogin) {
                        //todo
//                        startActivity(Intent(this, ShareActivity::class.java))
                    } else {
                        showToast(resources.getString(R.string.login_tint))
                        goLogin()
                    }
                }
                //?????? todo
                R.id.nav_setting -> {
//                    Intent(this@MainActivity, SettingActivity::class.java).run {
//                        // putExtra(Constant.TYPE_KEY, Constant.Type.SETTING_TYPE_KEY)
//                        startActivity(this)
//                    }
                }
                //R.id.nav_about_us -> {
                //    goCommonActivity(Constant.Type.ABOUT_US_TYPE_KEY)
                //}
                //????????????
                R.id.nav_logout -> {
                    logout()
                }
                //???????????? todo
                R.id.nav_night_mode -> {
                    if (SettingUtil.getIsNightMode()) {
                        SettingUtil.setIsNightMode(false)
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    } else {
                        SettingUtil.setIsNightMode(true)
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }
                    window.setWindowAnimations(R.style.WindowAnimationFadeInOut)
                    recreate()
                }
                //todo
                R.id.nav_todo -> {
                    if (isLogin) {
//                        Intent(this@MainActivity, TodoActivity::class.java).run {
//                            startActivity(this)
//                        }
                    } else {
                        showToast(resources.getString(R.string.login_tint))
                        goLogin()
                    }
                }
            }
            true
        }


    private fun goLogin() {
        //todo ??????????????????
//        Intent(this@MainActivity,LoginActivity::class.java).run {
//            startActivity(this)
//        }
    }

    private fun logout() {
        //todo ????????????
        DialogUtil.getConfirmDialog(this, resources.getString(R.string.confirm_logout),
            DialogInterface.OnClickListener { _, _ ->
                mDialog.show()
//                mPresenter?.logout()
            }).show()
    }

    /**
     * ???????????? Dialog
     */
    private val mDialog by lazy {
        DialogUtil.getWaitDialog(this@MainActivity, resources.getString(R.string.logout_ing))
    }

    /**
     * ??????Fragment
     * @param index
     */
    private fun showFragment(index: Int) {
        val transaction=supportFragmentManager.beginTransaction()
        hidFragments(transaction)
        mIndex=index
        Log.e("TAG","?????????????????????$mIndex");
        when(index){
            FRAGMENT_HOME ->{
                if (mHomeFragment==null){
                   mHomeFragment= HomeFragment.getInstance()
                    transaction.add(R.id.container,mHomeFragment!!,"home")
                }else{
                    transaction.show(mHomeFragment!!)
                }
            }
            FRAGMENT_SQUARE ->{
                if (mSquareFragment==null){
                    mSquareFragment=SquareFragment.getInstance()
                    transaction.add(R.id.container,mSquareFragment!!,"square")
                }else{
                    transaction.show(mSquareFragment!!)
                }
            }
            FRAGMENT_SYSTEM ->{
                if (mSystemFragment==null){
                    mSystemFragment=SystemFragment.getInstance()
                    transaction.add(R.id.container,mSystemFragment!!,"system")
                }else{
                    transaction.show(mSystemFragment!!)
                }
            }
            FRAGMENT_PROJECT ->{
                if (mProjectFragment==null){
                    mProjectFragment= ProjectFragment.getInstance()
                }else{
                    transaction.show(mProjectFragment!!)
                }
            }
            FRAGMENT_WECHAT ->{
                if (mWeChatFragment==null){
                    mWeChatFragment= WeChatFragment.getInstance()
                }else{
                    transaction.show(mWeChatFragment!!)
                }
            }
        }
        transaction.commit()
    }

    private fun hidFragments(transaction: FragmentTransaction) {
        mHomeFragment?.let { transaction.hide(it) }
        mSquareFragment?.let { transaction.hide(it) }
        mSystemFragment?.let { transaction.hide(it) }
        mProjectFragment?.let { transaction.hide(it) }
        mWeChatFragment?.let { transaction.hide(it) }
    }

    /**
     * NavigationItemSelect??????
     */
    private val onNavigationItemSelectedListener =BottomNavigationView.OnNavigationItemSelectedListener {
        return@OnNavigationItemSelectedListener when(it.itemId){
            R.id.action_home ->{
                showFragment(FRAGMENT_HOME)
                true
            }
            R.id.action_square -> {
                showFragment(FRAGMENT_SQUARE)
                true
            }
            R.id.action_system -> {
                showFragment(FRAGMENT_SYSTEM)
                true
            }
            R.id.action_project -> {
                showFragment(FRAGMENT_PROJECT)
                true
            }
            R.id.action_wechat -> {
                showFragment(FRAGMENT_WECHAT)
                true
            }
            else ->{
                false
            }
        }
    }

    /**
     * FAB ??????
     */
    private val onFABClickListener= View.OnClickListener {
        when(mIndex){
            FRAGMENT_HOME ->{
                mHomeFragment?.scrollToTop()
            }
            FRAGMENT_SQUARE -> {
                mSquareFragment?.scrollToTop()
            }
            FRAGMENT_SYSTEM -> {
                mSystemFragment?.scrollToTop()
            }
            FRAGMENT_PROJECT -> {
                mProjectFragment?.scrollToTop()
            }
            FRAGMENT_WECHAT -> {
                mWeChatFragment?.scrollToTop()
            }
        }
    }

}
