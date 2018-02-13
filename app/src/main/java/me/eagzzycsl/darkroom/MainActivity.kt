package me.eagzzycsl.darkroom

import android.content.Intent
import android.support.design.widget.FloatingActionButton
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View

import me.eagzzycsl.darkroom.ui.ActivityToolbar
import me.eagzzycsl.darkroom.ui.FragmentNaughty
import me.eagzzycsl.darkroom.manager.DarkManager
import me.eagzzycsl.darkroom.manager.AppList
import me.eagzzycsl.darkroom.manager.SettingsManager
import me.eagzzycsl.darkroom.utils.MyConfig
import me.eagzzycsl.darkroom.utils.PermissionChecker

class MainActivity : ActivityToolbar(), View.OnClickListener {
    private var naughtyFragment: FragmentNaughty? = null
    private var fab_add: FloatingActionButton? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (MyConfig.ifShowGuideActivity(this)) {
            startActivity(Intent(
                    this, GuideActivity::class.java
            ))
            finish()
            return
        }
        myFind()
        myRead()
        myCreate()
        mySet()
        SettingsManager.updateValues(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    private fun myFind() {
        fab_add = findViewById<View>(R.id.fab_add) as FloatingActionButton
        naughtyFragment = supportFragmentManager.findFragmentById(R.id.fragment_naughty) as FragmentNaughty
    }

    private fun myRead() {
        AppList.init(this)
        naughtyFragment?.updateData()
    }

    private fun myCreate() {

    }

    private fun mySet() {
        fab_add?.setOnClickListener(this)
        if (MyConfig.DEBUG && MyConfig.NO_CHECK_ACCESS) {
            return
        }
        PermissionChecker.requireAccessibility(this)
    }


    override fun onClick(view: View) {
        when (view.id) {
            R.id.fab_add -> {
                DarkManager.freezeAll(AppList.naughtyApps.filter { it.isEnable(this) }, this)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_edit -> {
                startActivity(Intent(
                        this,
                        NaughtyPickActivity::class.java
                ))
            }
            R.id.action_settings -> {
                startActivity(Intent(
                        this,
                        SettingsActivity::class.java
                ))
            }
            R.id.action_about -> {
                startActivity(Intent(
                        this,
                        AboutActivity::class.java
                ))
            }
        }
        return true
    }

}
