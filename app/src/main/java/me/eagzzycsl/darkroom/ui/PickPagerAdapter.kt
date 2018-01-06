package me.eagzzycsl.darkroom.ui

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class PickPagerAdapter(fm: FragmentManager, fragments: Array<FragmentOnDeviceApp>) : FragmentPagerAdapter(fm) {
    private val title = arrayOf("用户应用", "系统应用")
    private var fragments = arrayOf<FragmentOnDeviceApp>()

    init {
        this.fragments = fragments

    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return title[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }
}
