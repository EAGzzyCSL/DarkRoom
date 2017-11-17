package me.eagzzycsl.darkroom

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import me.eagzzycsl.darkroom.manager.AppList

import me.eagzzycsl.darkroom.utils.ConstantString

class JumpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jump)
        val intent = intent
        val pkgName = intent.getStringExtra(ConstantString.pkgName)
        val naughtyApp = AppList.getNaughtApp(pkgName)
        naughtyApp?.launch(this)
        finish()
    }


}
