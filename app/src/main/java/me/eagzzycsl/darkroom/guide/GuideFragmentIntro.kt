package me.eagzzycsl.darkroom.guide

import android.annotation.SuppressLint
import me.eagzzycsl.darkroom.GuideActivity
import me.eagzzycsl.darkroom.R


@SuppressLint("ValidFragment")
class GuideFragmentIntro : GuideFragment {
    constructor(guideEventListener: GuideActivity.GuideEventListener) {

    }
    constructor() {

    }
    override fun getLayoutId(): Int {
        return R.layout.layout_guide_intro
    }
}
