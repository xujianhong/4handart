package com.daomingedu.art.app.service

import android.app.IntentService
import android.content.Context
import android.content.Intent
import com.daomingedu.art.BuildConfig
import com.daomingedu.art.util.Log
import com.tencent.bugly.crashreport.CrashReport

/**
 * 初始化服务，防止在application的主线程中做耗时初始化
 * @author don
 */
class InitializeService : IntentService("InitializeService") {

    override fun onHandleIntent(intent: Intent?) {
        if (intent != null) {
            val action = intent.action
            if (ACTION_INIT_WHEN_APP_CREATE == action) {
                performInit()
            }
        }
    }

    private fun performInit() {
//        bugly
        //Bugly.init(applicationContext, BuildConfig.Bugly_APPID, BuildConfig.DEBUG)
        //Bugly.init(applicationContext, "0ad047f8b5", BuildConfig.DEBUG)
        CrashReport.initCrashReport(applicationContext, "129a61f258", false)
    }

    companion object {

        private val ACTION_INIT_WHEN_APP_CREATE = "com.daomingedu.art.service.action.INIT"

        fun start(context: Context) {
            val intent = Intent(context, InitializeService::class.java)
            intent.action = ACTION_INIT_WHEN_APP_CREATE
            context.startService(intent)
        }
    }

}
