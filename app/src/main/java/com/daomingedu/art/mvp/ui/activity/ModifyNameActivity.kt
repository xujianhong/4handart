package com.daomingedu.art.mvp.ui.activity

import android.content.Intent
import android.os.Bundle

import android.text.TextUtils
import android.view.View
import androidx.appcompat.widget.Toolbar

import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.daomingedu.art.di.component.DaggerModifyNameComponent
import com.daomingedu.art.di.module.ModifyNameModule
import com.daomingedu.art.mvp.contract.ModifyNameContract
import com.daomingedu.art.mvp.presenter.ModifyNamePresenter

import com.daomingedu.art.R
import com.daomingedu.art.util.StringUtils
import kotlinx.android.synthetic.main.activity_modify_name.*
import kotlinx.android.synthetic.main.include_page_head.*
import me.jessyan.autosize.internal.CancelAdapt


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/04/2019 18:51
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
class ModifyNameActivity : BaseActivity<ModifyNamePresenter>(), ModifyNameContract.View,CancelAdapt {
    private val name by lazy { intent.getStringExtra("name") }

    /**
     * 名称类型:0姓名，1昵称
     */
    private val nameType by lazy { intent.getIntExtra("nameType",0) }
    override fun requestUpdateCustomerSuccess() {
        ArmsUtils.makeText(application,"修改成功")
        val data = intent.apply {
            val strName = et_modify_name.getText().toString().trim { it <= ' ' }
            this.putExtra("name",strName)
        }
        setResult(RESULT_OK,data)
        finish()
    }

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerModifyNameComponent //如找不到该类,请编译一下项目
            .builder()
            .appComponent(appComponent)
            .modifyNameModule(ModifyNameModule(this))
            .build()
            .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_modify_name //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        tv_name.text = if(nameType == 1){"修改昵称"}else{"修改姓名"}
        val toolbar = findViewById<Toolbar>(R.id.toolbar2)
        toolbar.background.alpha = 255
        toolbar.setNavigationIcon(R.drawable.ic_back_white)
        toolbar.setNavigationOnClickListener { killMyself() }
        toolbar.inflateMenu(R.menu.menu_name_save)
        toolbar.setOnMenuItemClickListener {
            val strName = et_modify_name.getText().toString().trim({ it <= ' ' })
            if (it.getItemId() == R.id.save) {//保存
                if (TextUtils.isEmpty(strName)) {
                    ArmsUtils.makeText(this,"姓名不能为空")
                    return@setOnMenuItemClickListener false
                }
                if (strName.length > 5) {
                    ArmsUtils.makeText(this,"只能输入5个字")
                    et_modify_name.setText("")
                    return@setOnMenuItemClickListener false
                }
                if (StringUtils.containsEmoji(strName)) {
                    ArmsUtils.makeText(this,"不能输入表情")
                    return@setOnMenuItemClickListener false
                }
                mPresenter?.updateCustomer(strName,nameType)
            }
            return@setOnMenuItemClickListener false
        }
        et_modify_name.setText(name)
    }


    override fun showLoading() {

    }

    override fun hideLoading() {

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
