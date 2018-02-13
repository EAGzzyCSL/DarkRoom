package me.eagzzycsl.darkroom.guide

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.eagzzycsl.darkroom.GuideActivity
@SuppressLint("ValidFragment")
abstract class GuideFragment : Fragment {

    constructor(guideEventListener: GuideActivity.GuideEventListener) {

    }

    constructor() {

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(getLayoutId(), container, false)
        return view
    }

    abstract fun getLayoutId(): Int



}