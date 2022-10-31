package com.daomingedu.art.mvp.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.KeyEvent
import android.view.Window
import android.view.WindowManager
import android.widget.Toast

import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.daomingedu.art.di.component.DaggerSelectImageVideoComponent
import com.daomingedu.art.di.module.SelectImageVideoModule
import com.daomingedu.art.mvp.contract.SelectImageVideoContract
import com.daomingedu.art.mvp.presenter.SelectImageVideoPresenter

import com.daomingedu.art.R
import com.daomingedu.art.mvp.ui.widget.selectimagevideo.SelectImageVideoView
import com.daomingedu.art.mvp.ui.widget.selectimagevideo.listener.GetFilePathListener
import com.daomingedu.art.util.OSUtils
import kotlinx.android.synthetic.main.activity_select_image_video.*
import me.jessyan.autosize.internal.CancelAdapt
import java.io.File
import java.util.*


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/07/2019 20:57
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
class SelectImageVideoActivity : BaseActivity<SelectImageVideoPresenter>(), SelectImageVideoContract.View,CancelAdapt{
    companion object {
        val REQUEST_CAMERA = 0x001
        @JvmField
        val KEY_RESULT = "Image_Video_Result"
        @JvmField
        val KEY_TYPE = "Image_Or_Video"
        @JvmField
        val KEY_CAMERA_SHOW = "Camera_Show"
        @JvmField
        val KEY_SELECT_NUM = "Select_Num"
    }
    //查询数据类型
    val typeData by lazy {
        intent.getIntExtra(KEY_TYPE, SelectImageVideoView.TYPE_IMAGE)//默认选择头像
    }
    //是否显示照相
    val cameraShow by lazy {
        intent.getBooleanExtra(KEY_CAMERA_SHOW, true);//默认显示
    }
    //选择数量
    val selectNum by lazy {
        if (intent.getIntExtra(KEY_SELECT_NUM, 1) <= 0) {
            0
        }else{
            intent.getIntExtra(KEY_SELECT_NUM, 1)
        }
    }

    private var mSelectList = ArrayList<String>()

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerSelectImageVideoComponent //如找不到该类,请编译一下项目
            .builder()
            .appComponent(appComponent)
            .selectImageVideoModule(SelectImageVideoModule(this))
            .build()
            .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_select_image_video //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        if (OSUtils.isMIUI6More() || OSUtils.isFlymeOS4More()
            || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                //透明状态栏
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            }
        }
        siv_view.addGetFilePathListener(object :GetFilePathListener{
            override fun openCamera() {
                // 创建临时相机文件
                siv_view.setFileCamera(
                    File(
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                        Date().time.toString() + ".jpg"
                    )
                )
                // 跳转到系统照相机
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (cameraIntent.resolveActivity(packageManager) != null) {
                    // 设置系统相机拍照后的输出路径
                    cameraIntent.putExtra(
                        MediaStore.EXTRA_OUTPUT, Uri.fromFile(
                            siv_view.getFileCamera()
                        )
                    )
                    startActivityForResult(cameraIntent, REQUEST_CAMERA)
                } else {
                    Toast.makeText(
                        applicationContext,
                        "没有找到摄像头", Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun getFilePath(path: String?) {
                mSelectList.clear()
                mSelectList.add(path!!)
                returnData()
            }

            override fun getFilelistPath(path: ArrayList<String>?) {
                mSelectList.clear()
                mSelectList = path!!
                returnData()
            }
        })

        siv_view.setData(typeData, selectNum, cameraShow)

        siv_view.arrawBack.setOnClickListener {
            finish()
        }
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
    private fun returnData() {
        val data = Intent()
        data.putStringArrayListExtra(KEY_RESULT, mSelectList)
        setResult(RESULT_OK, data)
        finish()
        //        // 返回已选择的图片数据
        //        Intent data = new Intent();
        //        data.putStringArrayListExtra(KEY_RESULT, mSelectList);
        //        setResult(RESULT_OK, data);
        //        finish();
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_CAMERA//照相
                -> if (siv_view.getFileCamera().exists()) {
                    sendBroadcast(
                        Intent(
                            Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                            Uri.parse("file://" + siv_view.getFileCamera().getAbsolutePath())
                        )
                    )
                    if (!mSelectList.contains(siv_view.getFileCamera().getAbsolutePath())) {
                        mSelectList.add(siv_view.getFileCamera().getAbsolutePath())
                    }
                    returnData()
                }
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish()
            return true
        }
        return false
    }
}
