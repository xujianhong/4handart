package com.daomingedu.art.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils

import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.daomingedu.art.di.component.DaggerModifyPasswordComponent
import com.daomingedu.art.di.module.ModifyPasswordModule
import com.daomingedu.art.mvp.contract.ModifyPasswordContract
import com.daomingedu.art.mvp.presenter.ModifyPasswordPresenter

import com.daomingedu.art.R
import com.daomingedu.art.app.onClick
import com.daomingedu.art.mvp.ui.widget.LoadingDialog
import kotlinx.android.synthetic.main.activity_modify_password.*
import org.jetbrains.anko.startActivity


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/27/2019 16:32
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
/**
 * 如果没presenter
 * 你可以这样写
 *
 * @ActivityScope(請注意命名空間) class NullObjectPresenterByActivity
 * @Inject constructor() : IPresenter {
 * override fun onStart() {
 * }
 *
 * override fun onDestroy() {
 * }
 * }
 */
class ModifyPasswordActivity : BaseActivity<ModifyPasswordPresenter>(), ModifyPasswordContract.View {
    val loadingDialog: LoadingDialog by lazy {
        LoadingDialog(this)
    }
    override fun requestChangePswSuccess() {
        ArmsUtils.makeText(application,"修改密码成功,请重新登录")
        startActivity<LoginActivity>()
    }

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerModifyPasswordComponent //如找不到该类,请编译一下项目
            .builder()
            .appComponent(appComponent)
            .modifyPasswordModule(ModifyPasswordModule(this))
            .build()
            .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_modify_password //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        title = "修改密码"
        btnComplete.onClick {
            val oldPwd = etInputOldPwd.text.toString().trim()
            if (TextUtils.isEmpty(oldPwd)) {
                ArmsUtils.makeText(application,"旧密码不能为空")
                return@onClick
            }
            val pwd = etInputNewPwd.text.toString().trim()
            if (TextUtils.isEmpty(pwd)) {
                ArmsUtils.makeText(application,"密码不能为空")
                return@onClick
            }
            val pwdAgain = etInputNewPwdAgain.text.toString().trim()
            if (pwd != pwdAgain) {
                ArmsUtils.makeText(application,"两次密码不相同")
                return@onClick
            }
            mPresenter?.changePsw(oldPwd,pwd)
        }
    }


    override fun showLoading() {
        loadingDialog.show()
    }

    override fun hideLoading() {
        loadingDialog.dismiss()
    }

    override fun showMessage(message: String) {
        ArmsUtils.snackbarText(message)
    }

    override fun launchActivity(intent: Intent) {
        ArmsUtils.startActivity(intent)
    }

    override fun killMyself() {
        finish()
    }
}
