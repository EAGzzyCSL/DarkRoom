package me.eagzzycsl.darkroom.ui

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import me.eagzzycsl.darkroom.guide.GuideFragment

class GuidePagerAdapter(fm: FragmentManager, fragments: Array<GuideFragment>) : FragmentPagerAdapter(fm) {
    private var fragments = arrayOf<GuideFragment>()

    init {
        this.fragments = fragments

    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }
}
