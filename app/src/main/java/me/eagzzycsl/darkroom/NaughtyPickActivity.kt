package me.eagzzycsl.darkroom

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.View

import me.eagzzycsl.darkroom.manager.AppList
import me.eagzzycsl.darkroom.model.NaughtyApp
import me.eagzzycsl.darkroom.ui.ActivityToolbar
import me.eagzzycsl.darkroom.ui.FragmentOnDeviceApp
import me.eagzzycsl.darkroom.ui.FragmentSysApp
import me.eagzzycsl.darkroom.ui.FragmentUserApp
import me.eagzzycsl.darkroom.ui.PickPagerAdapter
import java.util.*

class NaughtyPickActivity : ActivityToolbar(), View.OnClickListener {
    private var pick_viewPager: ViewPager? = null
    private var pick_tab: TabLayout? = null
    private val fragmentSysApp = FragmentSysApp()
    private val fragmentUserApp = FragmentUserApp()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pick_tab = findViewById<TabLayout>(R.id.pick_tab)
        pick_viewPager = findViewById<ViewPager>(R.id.pick_pager)
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener(this)
        pick_tab?.setupWithViewPager(pick_viewPager)
        pick_viewPager?.adapter = PickPagerAdapter(
                supportFragmentManager,
                arrayOf<FragmentOnDeviceApp>(fragmentUserApp, fragmentSysApp)
        )
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_naughty_pick
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.fab -> {
                val naughtyApps = LinkedList<NaughtyApp>()
                naughtyApps.addAll(fragmentUserApp.genSelectedApps())
                naughtyApps.addAll(fragmentSysApp.genSelectedApps())
                AppList.saveNaughtyApps(this, naughtyApps)
                finish()
            }
        }
    }
}
