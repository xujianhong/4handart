package com.daomingedu.art.mvp.ui.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.daomingedu.art.di.component.DaggerCertificationComponent
import com.daomingedu.art.di.module.CertificationModule
import com.daomingedu.art.mvp.contract.CertificationContract
import com.daomingedu.art.mvp.presenter.CertificationPresenter

import com.daomingedu.art.R
import com.daomingedu.art.app.loadImage
import com.daomingedu.art.app.onClick
import com.daomingedu.art.app.visible
import com.daomingedu.art.mvp.model.entity.CheckCerBean
import com.daomingedu.art.mvp.ui.widget.LoadingDialog
import com.daomingedu.art.util.startActivityForResult
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_certification.*
import org.jetbrains.anko.startActivity
import java.io.File


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/13/2019 21:25
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
class CertificationActivity : BaseActivity<CertificationPresenter>(), CertificationContract.View {

    private val loading by lazy { LoadingDialog(this, tips = "上传中") }
    private val checkCerBean by lazy { intent.getSerializableExtra("data") as CheckCerBean }
    private var imgPath: String? = null

    companion object {
        fun start(context: Context, data: CheckCerBean?) {
            context.startActivity<CertificationActivity>("data" to data)
        }
    }

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerCertificationComponent //如找不到该类,请编译一下项目
            .builder()
            .appComponent(appComponent)
            .certificationModule(CertificationModule(this))
            .build()
            .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_certification //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        title = "实名认证"
        ivPhoto.onClick {
            RxPermissions(this).request(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
                .subscribe {
                    if (it) {
                        startActivityForResult<CerCameraActivity>(callback = { t ->
                            imgPath = t.getStringExtra("imgPath")
                            Glide.with(this)
                                .load(File(imgPath))
                                .apply(RequestOptions().apply {
                                    error(R.drawable.upload_photo_button)
                                    placeholder(R.drawable.upload_photo_button)
                                })
                                .into(ivPhoto)
                        })
                    } else {
                        ArmsUtils.makeText(application, "缺少相机相关权限")
                    }
                }
        }

        if (checkCerBean.state == 1) {
            ivPhoto.loadImage(checkCerBean.cerPath)
            etName.setText(checkCerBean.name)
            etName.isFocusable = false
            etName.isFocusableInTouchMode = false
            etIdCard.setText(checkCerBean.idNumber)
            etIdCard.isFocusable = false
            etIdCard.isFocusableInTouchMode = false
            ivPhoto.isEnabled = false
            btnSubmit.visible(false)
        } else {
            if (!TextUtils.isEmpty(checkCerBean.phonePath)) {
                ivPhoto.loadImage(checkCerBean.phonePath)
            }
            if (!TextUtils.isEmpty(checkCerBean.name)){
                etName.setText(checkCerBean.name)
            }
            if (!TextUtils.isEmpty(checkCerBean.idNumber)){
                etIdCard.setText(checkCerBean.idNumber)
            }
        }

        btnSubmit.onClick {
            if (TextUtils.isEmpty(imgPath)) {
                ArmsUtils.makeText(application, "寸照不能为空")
                return@onClick
            }
            val name = etName.text.toString().trim()
            if (TextUtils.isEmpty(name)) {
                ArmsUtils.makeText(application, "姓名不能为空")
                return@onClick
            }
            val idCard = etIdCard.text.toString().trim()
            if (TextUtils.isEmpty(idCard)) {
                ArmsUtils.makeText(application, "身份证不能为空")
                return@onClick
            }
            mPresenter?.uploadFile(imgPath!!, name, idCard)
        }
    }


    override fun showLoading() {
        loading.show()
    }

    override fun hideLoading() {
        loading.dismiss()
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

    override fun requestSaveCerSuccess() {
        finish()
    }
}
