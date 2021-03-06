package com.bihe0832.android.test

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.support.v4.content.ContextCompat
import android.view.View
import com.bihe0832.android.common.floatview.IconManager
import com.bihe0832.android.framework.ZixieContext
import com.bihe0832.android.framework.router.RouterConstants
import com.bihe0832.android.framework.ui.main.CommonActivity
import com.bihe0832.android.lib.adapter.CardInfoHelper
import com.bihe0832.android.lib.immersion.hideBottomUIMenu
import com.bihe0832.android.lib.permission.PermissionManager
import com.bihe0832.android.lib.router.annotation.APPMain
import com.bihe0832.android.lib.router.annotation.Module
import com.bihe0832.android.lib.sqlite.impl.CommonDBManager

@APPMain
@Module(RouterConstants.MODULE_NAME_DEBUG)
class TestMainActivity : CommonActivity() {
    val LOG_TAG = "TestHttpActivity"
    val mIconManager by lazy {
        IconManager(this, "").apply {
            setIconClickListener(View.OnClickListener {
                ZixieContext.showToast("点了一下Icon")
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolbar("TestMainActivity", false)

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())
        }


        CardInfoHelper.getInstance().setAutoAddItem(true)
        PermissionManager.addPermissionDesc(HashMap<String, String>().apply {
            put(Manifest.permission.CAMERA, "相机")
            put(Manifest.permission.RECORD_AUDIO, "录音")
            put(Manifest.permission.SYSTEM_ALERT_WINDOW, "悬浮窗")
            put(Manifest.permission.WRITE_EXTERNAL_STORAGE, "访问存储卡")
        })

        PermissionManager.addPermissionScene(HashMap<String, String>().apply {
            put(Manifest.permission.CAMERA, "扫描二维码")
            put(Manifest.permission.RECORD_AUDIO, "语音录制")
            put(Manifest.permission.SYSTEM_ALERT_WINDOW, "悬浮窗")
            put(Manifest.permission.WRITE_EXTERNAL_STORAGE, "存储权限测试")
        })

//        PermissionManager.checkPermission(this, true, getPermissionResult(), Manifest.permission.CAMERA)
//        UpdateManager.checkUpdateAndShowDialog(this, false)
        hideBottomUIMenu()
        CommonDBManager.init(this)
    }

    override fun getStatusBarColor(): Int {
        return ContextCompat.getColor(this, R.color.white)
    }

    override fun getNavigationBarColor(): Int {
        return ContextCompat.getColor(this, R.color.result_point_color)
    }

    override fun getPermissionList(): List<String> {
        return ArrayList<String>().apply {
            add(Manifest.permission.CAMERA)
            add(Manifest.permission.RECORD_AUDIO)
            add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            add(Manifest.permission.SYSTEM_ALERT_WINDOW)
        }
    }

    override fun getPermissionResult(): PermissionManager.OnPermissionResult {
        return object : PermissionManager.OnPermissionResult {
            override fun onSuccess() {
                ZixieContext.showDebug("用户授权成功")
            }

            override fun onUserCancel() {
                ZixieContext.showLongToast("用户放弃授权")
            }

            override fun onUserDeny() {
                ZixieContext.showDebug("用户拒绝授权")
            }

            override fun onFailed(msg: String) {
                ZixieContext.showDebug("用户授权失败")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (findFragment(TestMainFragment::class.java) == null) {
            loadRootFragment(R.id.common_fragment_content, TestMainFragment())
        }
//        mIconManager.showIcon()
//        hideBottomUIMenu()
    }


    override fun onBackPressedSupport() {
        super.onBackPressedSupport()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}
