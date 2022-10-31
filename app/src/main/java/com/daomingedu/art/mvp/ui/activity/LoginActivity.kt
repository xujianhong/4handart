package com.daomingedu.art.mvp.ui.activity

import android.Manifest
import android.content.Intent
import android.os.Bundle

import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.daomingedu.art.di.component.DaggerLoginComponent
import com.daomingedu.art.di.module.LoginModule
import com.daomingedu.art.mvp.contract.LoginContract
import com.daomingedu.art.mvp.presenter.LoginPresenter

import com.daomingedu.art.R
import com.daomingedu.art.app.Constant
import com.daomingedu.art.app.Preference
import com.daomingedu.art.app.onClick
import com.daomingedu.art.mvp.model.entity.AboutUsBean
import com.daomingedu.art.mvp.ui.widget.LoadingDialog
import com.jess.arms.integration.AppManager
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.cbAgree
import kotlinx.android.synthetic.main.activity_login.etInputPhoneNumber
import kotlinx.android.synthetic.main.activity_login.etInputPwd
import kotlinx.android.synthetic.main.activity_login.register_user
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.startActivity


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/24/2019 23:28
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
class LoginActivity : BaseActivity<LoginPresenter>(), LoginContract.View {
    var isFirst by Preference("isFirst", true)
    val loadingDialog: LoadingDialog by lazy {
        LoadingDialog(this)
    }
    override fun requestLoginSuccess() {
        startActivity<MainActivity>()
    }

    override fun requestAboutUsSuccess(data: AboutUsBean?, type: Int) {
        data?.let {
            if(type==2){
                startActivity<CommonWebActivity>(Constant.URL_EXTRA to it.serviceAgent, Constant.TITLE_EXTRA to "联系客服")
            }
        }
    }

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerLoginComponent //如找不到该类,请编译一下项目
            .builder()
            .appComponent(appComponent)
            .loginModule(LoginModule(this))
            .build()
            .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_login //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        AppManager.getAppManager().killAll(LoginActivity::class.java)
        tvForgetPwd.onClick {
            startActivity<ForgetPasswordActivity>()
        }
        tvRegister.onClick {
            startActivity<RegisterActivity>()
        }
        register_user.onClick {
            startActivity<CommonWebActivity>(Constant.URL_EXTRA to "http://4hand.com.cn/art2.html",  Constant.TITLE_EXTRA to "隐私政策")
        }
        btnLogin.onClick {
            if(!cbAgree.isChecked){
                ArmsUtils.makeText(application, "请勾选同意相关协议政策")
                return@onClick
            }
            val mobile = etInputPhoneNumber.text.toString().trim()
            if (TextUtils.isEmpty(mobile)) {
                ArmsUtils.makeText(application,"手机号不能为空")
                return@onClick
            }
            val pwd = etInputPwd.text.toString().trim()
            if (TextUtils.isEmpty(pwd)) {
                ArmsUtils.makeText(application,"密码不能为空")
                return@onClick
            }
            mPresenter?.login(mobile,pwd)
        }
        btnCustomer.onClick {
            mPresenter?.aboutUs(2)
        }

        if(isFirst)
            showDialog()
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

    private fun showDialog() {
        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.show()
        alertDialog.setCancelable(false)
        val window = alertDialog.getWindow();
        if (window != null) {
            window.setContentView(R.layout.diag_privacy);
            window.setGravity(Gravity.CENTER);

            val tvContent: TextView = window.findViewById(R.id.tv_content);
            val tvCancel: TextView = window.findViewById(R.id.tv_cancel);
            val tvAgree: TextView = window.findViewById(R.id.tv_agree)


            val str = "    感谢您对本公司的支持!本公司非常重视您的个人信息和隐私保护。" +
                    "为了更好地保障您的个人权益，在您使用我们的产品前，" +
                    "请务必审慎阅读《隐私政策》内的所有条款，" +
                    "尤其是:\n" +
                    " 1.我们对您的个人信息的收集/保存/使用/对外提供/保护等规则条款，以及您的用户权利等条款;\n" +
                    " 2. 约定我们的限制责任、免责条款;\n" +
                    " 3.其他以颜色或加粗进行标识的重要条款。\n" +
                    "您点击“同意并继续”的行为即表示您已阅读完毕并同意以上协议的全部内容。" +
                    "如您同意以上协议内容，请点击“同意”，开始使用我们的产品和服务!";

            val ssb = SpannableStringBuilder();
            ssb.append(str);
            val start = str.indexOf("《");//第一个出现的位置
            ssb.setSpan(object : ClickableSpan() {

                override fun onClick(widget: View) {
                    startActivity<CommonWebActivity>(Constant.URL_EXTRA to "http://4hand.com.cn/art2.html",  Constant.TITLE_EXTRA to "隐私政策")
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.color = getResources().getColor(R.color.colorPrimary);
                    ds.isUnderlineText = false;
                }
            }, start, start + 6, 0);

//            val end = str.lastIndexOf("《");
//            ssb.setSpan(object : ClickableSpan() {
//
//                override fun onClick(widget: View) {
//                    mPresenter?.getProtocol(1)
//                }
//
//                override fun updateDrawState(ds: TextPaint) {
//                    super.updateDrawState(ds)
//                    ds.color = getResources().getColor(R.color.green_700);
//                    ds.isUnderlineText = false;
//                }
//            }, end, end + 6, 0);

            tvContent.movementMethod = LinkMovementMethod.getInstance();
            tvContent.setText(ssb, TextView.BufferType.SPANNABLE);


            tvCancel.setOnClickListener {
                alertDialog.cancel();
                finish();
            };

            tvAgree.setOnClickListener {
                isFirst = false
                alertDialog.cancel();
            }

        }

    }
}
