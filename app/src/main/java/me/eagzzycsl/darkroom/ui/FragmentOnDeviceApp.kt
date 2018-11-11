package me.eagzzycsl.darkroom.ui

import android.os.Bundle

import java.util.ArrayList

import me.eagzzycsl.darkroom.manager.AppList
import me.eagzzycsl.darkroom.model.MetaApp


abstract class FragmentOnDeviceApp : BaseFragment<MetaApp>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppList.markFrozen()
        setData(getOnDeviceApps())
    }

    abstract fun getOnDeviceApps(): ArrayList<MetaApp>


    fun genSelectedApps(): List<MetaApp> {
        return getOnDeviceApps().filter { it.willFreeze }
    }

    override fun getAdapter(): BaseAdapter<MetaApp> {
        return AdapterOnDeviceApp(activity)
    }
}
