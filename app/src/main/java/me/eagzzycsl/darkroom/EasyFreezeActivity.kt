package me.eagzzycsl.darkroom

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.View
import me.eagzzycsl.darkroom.manager.AppList
import me.eagzzycsl.darkroom.ui.ActivityToolbar
import me.eagzzycsl.darkroom.ui.FragmentEasyFreeze
import me.eagzzycsl.darkroom.utils.ConstantString

class EasyFreezeActivity : ActivityToolbar(), View.OnClickListener {
    private var easyFreezeFragment: FragmentEasyFreeze? = null
    private var fabConfirm: FloatingActionButton? = null
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fab_confirm -> {
                val naughtyApps = easyFreezeFragment?.genSelectedApps()
                if (naughtyApps != null) {
                    AppList.appendNaughtyApps(this, naughtyApps)
                }
                finish()
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_easy_freeze
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appName = intent.getStringExtra(ConstantString.easyFreezeExtraKeyAppName)
        AppList.genEasyFreezeApps(appName)
        easyFreezeFragment = supportFragmentManager.findFragmentById(R.id.fragment_easy_freeze) as FragmentEasyFreeze
        easyFreezeFragment?.updateData()
        fabConfirm = findViewById<FloatingActionButton>(R.id.fab_confirm)
        fabConfirm?.setOnClickListener(this)
    }
}
