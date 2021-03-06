package com.bihe0832.android.framework.router

import android.content.Context
import android.net.Uri
import com.bihe0832.android.framework.constant.Constants
import com.bihe0832.android.framework.log.LoggerFile
import com.bihe0832.android.lib.config.Config
import com.bihe0832.android.lib.router.RouterContext

/**
 * Created by hardyshi on 2017/6/27.
 *
 */
object RouterInterrupt {

    val MODULE_NAME_ROUTER = "Router"

    interface RouterProcess {
        //是否需要登录
        fun needLogin(uri: Uri): Boolean

        //是否需要拦截
        fun needInterrupt(uri: Uri): Boolean

        //拦截后操作
        fun doInterrupt(uri: Uri): Boolean
    }

    @Synchronized
    fun init(process: RouterProcess) {
        RouterContext.setGlobalRouterCallback(object : RouterContext.RouterCallback {
            override fun afterOpen(context: Context, uri: Uri) {
                logRouterToFile("afterOpen ->$uri")
            }

            //跳转前拦截
            override fun beforeOpen(context: Context, uri: Uri): Boolean {
                logRouterToFile("beforeOpen ->$uri")
                return if (process.needInterrupt(uri) || process.needLogin(uri)) {
                    //拦截处理结果
                    logRouterToFile("needInterrupt uri ->$uri")
                    process.doInterrupt(uri)
                } else {
                    false
                }
            }

            override fun error(context: Context, uri: Uri, e: Throwable) {
                logRouterToFile("error ->$uri")
            }

            override fun notFound(context: Context, uri: Uri) {
                logRouterToFile("notFound ->$uri")
            }
        })
    }

    fun hasAgreedPrivacy(): Boolean {
        return Config.isSwitchEnabled(Constants.CONFIG_KEY_PRIVACY_AGREEMENT_ENABLED, false)
    }

    fun logRouterToFile(msg: String) {
        LoggerFile.log(getRouterLogPath(), msg)

    }

    fun getRouterLogPath(): String {
        return LoggerFile.getZixieFileLogPathByModule(MODULE_NAME_ROUTER)

    }
}
