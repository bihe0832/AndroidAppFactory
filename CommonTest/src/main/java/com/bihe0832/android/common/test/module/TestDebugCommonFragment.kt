package com.bihe0832.android.common.test.module

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.View
import com.bihe0832.android.common.feedback.FeedbackActivity.MODULE_NAME_FEEDBACK
import com.bihe0832.android.common.praise.UserPraiseManager
import com.bihe0832.android.common.test.base.BaseTestFragment
import com.bihe0832.android.common.test.item.TestItemData
import com.bihe0832.android.common.test.item.TestTipsData
import com.bihe0832.android.common.test.log.TestLogActivity
import com.bihe0832.android.framework.ZixieContext
import com.bihe0832.android.framework.router.RouterAction
import com.bihe0832.android.framework.router.RouterConstants
import com.bihe0832.android.lib.adapter.CardBaseModule
import com.bihe0832.android.lib.file.FileUtils
import com.bihe0832.android.lib.file.select.FileSelectTools
import com.bihe0832.android.lib.lifecycle.*
import com.bihe0832.android.lib.utils.DateUtil
import com.bihe0832.android.lib.utils.apk.APKUtils
import com.bihe0832.android.lib.utils.intent.IntentUtils
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.set

open class TestDebugCommonFragment : BaseTestFragment() {

    override fun getDataList(): ArrayList<CardBaseModule> {
        return ArrayList<CardBaseModule>().apply {
            add(TestTipsData("APPFactory的通用组件和工具"))
            add(TestItemData("查看应用版本及环境", View.OnClickListener { showAPPInfo() }))
            add(TestItemData("查看使用情况", View.OnClickListener { showUsedInfo() }))
            add(TestItemData("查看设备信息", View.OnClickListener { showMobileInfo() }))
            add(TestItemData("查看第三方应用信息", View.OnClickListener { showOtherAPPInfo() }))
            add(TestItemData("日志管理", View.OnClickListener {
                showLog()
            }))
            add(TestItemData("弹出评分页面", View.OnClickListener {
                UserPraiseManager.showUserPraiseDialog(activity!!, RouterAction.getFinalURL(MODULE_NAME_FEEDBACK))
            }))
            add(TestItemData("打开反馈页面", View.OnClickListener {
                val map = HashMap<String, String>()
                map[RouterConstants.INTENT_EXTRA_KEY_WEB_URL] = Uri.encode("https://support.qq.com/product/290858")
                RouterAction.openPageByRouter(MODULE_NAME_FEEDBACK, map)
            }))
            add(TestItemData("打开应用设置") { IntentUtils.startAppDetailSettings(context) })
            add(TestItemData("打开开发者模式") {
                IntentUtils.startSettings(context, android.provider.Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS)
            })
        }
    }

    protected fun startActivity(activityName: String) {
        try {
            val threadClazz = Class.forName(activityName)
            val intent = Intent(context, threadClazz)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
            ZixieContext.showToast("请确认当前运行的测试模块是否包含该应用")
        }
    }

    protected fun showLog() {
        startActivity(TestLogActivity::class.java)
    }

    protected fun showMobileInfo() {
        val builder = StringBuilder()
        builder.append("PackageName: ${context!!.packageName}\n")
        builder.append("deviceId: ${ZixieContext.getDeviceId()}\n")
        builder.append("厂商&型号: ${Build.MANUFACTURER}, ${Build.MODEL}, ${Build.BRAND}\n")
        builder.append("系统版本: ${Build.VERSION.RELEASE}, ${Build.VERSION.SDK_INT}\n")
        builder.append("系统指纹: ${Build.FINGERPRINT}\n")

        showInfo("分享设备信息给开发者", builder.toString())
    }

    protected fun showAPPInfo() {
        val builder = StringBuilder()
        var version = if (ZixieContext.isDebug()) {
            "内测版"
        } else {
            if (ZixieContext.isOfficial()) {
                "外发版"
            } else {
                "预发布版"
            }
        }
        builder.append("应用名称: ${APKUtils.getAppName(context)}\n")
        builder.append("应用包名: ${ZixieContext.applicationContext!!.packageName}\n")
        builder.append("安装时间: ${DateUtil.getDateEN(LifecycleHelper.getVersionInstalledTime())}\n")
        builder.append("版本类型: $version\n")
        builder.append("应用版本: ${ZixieContext.getVersionName()}.${ZixieContext.getVersionCode()}\n")
        builder.append("版本标识: ${ZixieContext.getVersionTag()}\n")
        builder.append("签名MD5: ${APKUtils.getSigMd5ByPkgName(ZixieContext.applicationContext, ZixieContext.applicationContext?.packageName)}")

        showInfo("应用信息", builder.toString())
    }

    protected fun showUsedInfo() {
        val builder = StringBuilder()

        builder.append("应用安装时间: ${DateUtil.getDateEN(LifecycleHelper.getAPPInstalledTime())}\n")
        builder.append("当前版本安装时间: ${DateUtil.getDateEN(LifecycleHelper.getVersionInstalledTime())}\n")

        builder.append("上次启动版本号: ${LifecycleHelper.getAPPLastVersion()}\n")
        builder.append("上次启动时间: ${DateUtil.getDateEN(LifecycleHelper.getAPPLastStartTime())}\n")

        builder.append("本次启动类型: ${
            LifecycleHelper.isFirstStart.let {
                when (it) {
                    INSTALL_TYPE_NOT_FIRST -> "非首次启动"
                    INSTALL_TYPE_VERSION_FIRST -> "版本首次启动"
                    INSTALL_TYPE_APP_FIRST -> "应用首次启动"
                    else -> "类型错误（$it）"
                }
            }
        }\n")
        builder.append("本次启动时间: ${DateUtil.getDateEN(ApplicationObserver.getAPPStartTime())}\n")
        builder.append("累积使用天数: ${LifecycleHelper.getAPPUsedDays()}\n")
        builder.append("累积使用次数: ${LifecycleHelper.getAPPUsedTimes()}\n")
        builder.append("当前版本使用次数: ${LifecycleHelper.getCurrentVersionUsedTimes()}\n")

        builder.append("最后一次退后台: ${DateUtil.getDateEN(ApplicationObserver.getLastPauseTime())}\n")
        builder.append("最后一次回前台: ${DateUtil.getDateEN(ApplicationObserver.getLastResumedTime())}\n")

        builder.append("当前页面: ${ActivityObserver.getCurrentActivity()?.javaClass?.name}\n")
        showInfo("应用使用情况", builder.toString())
    }

    protected fun showOtherAPPInfo() {
        val builder = StringBuilder()
        builder.append("第三方应用信息:\n\n")
        addPackageInfo("com.tencent.mobileqq", builder)
        addPackageInfo("com.tencent.mm", builder)
        addPackageInfo("com.tencent.qqlite", builder)
        addPackageInfo("com.tencent.mobileqqi", builder)
        addPackageInfo("com.tencent.tim", builder)
        sendInfo("分享第三方应用信息给开发者", builder.toString())
    }

    protected fun addPackageInfo(packageName: String, builder: StringBuilder) {
        val info = APKUtils.getInstalledPackage(context, packageName)
        builder.append("\n$packageName: ")
        if (null == info) {
            builder.append("未安装")
        } else {
            builder.append("\n\tname: ${APKUtils.getAppName(context, packageName)}\n")
            builder.append("	versionName: ${info.versionName}\n")
            builder.append("	versionCode: ${info.versionCode}\n")
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FileSelectTools.FILE_CHOOSER && resultCode == RESULT_OK) {
            data?.extras?.getString(FileSelectTools.INTENT_EXTRA_KEY_WEB_URL, "")?.let { filePath ->
                FileUtils.sendFile(activity!!, filePath, "*/*").let {
                    if (!it) {
                        ZixieContext.showToast("分享文件:$filePath 失败")
                    }
                }
            }
        }
    }
}