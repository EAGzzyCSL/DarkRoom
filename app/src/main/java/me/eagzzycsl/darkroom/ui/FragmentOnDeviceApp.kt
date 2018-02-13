package me.eagzzycsl.darkroom.ui

import android.os.Bundle

import java.util.ArrayList

import me.eagzzycsl.darkroom.manager.AppList
import me.eagzzycsl.darkroom.model.NaughtyApp
import me.eagzzycsl.darkroom.model.OnDeviceApp


abstract class FragmentOnDeviceApp : BaseFragment<OnDeviceApp>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.markFrozen()
        setData(getOnDeviceApps())
    }

    abstract fun getOnDeviceApps(): ArrayList<OnDeviceApp>

    private fun markFrozen() {
        getOnDeviceApps().forEach { it -> it.frozen = AppList.isNaughtyApp(it) }
    }


    fun genSelectedApps(): List<NaughtyApp> {
        return getOnDeviceApps().filter { it.frozen }.map { it.toNaughtyApp() }
    }

    override fun getAdapter(): BaseAdapter<OnDeviceApp> {
        return AdapterOnDeviceApp(activity)
    }
}
