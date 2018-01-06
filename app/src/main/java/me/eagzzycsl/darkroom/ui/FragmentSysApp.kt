package me.eagzzycsl.darkroom.ui

import java.util.ArrayList

import me.eagzzycsl.darkroom.manager.AppList
import me.eagzzycsl.darkroom.model.OnDeviceApp


class FragmentSysApp : FragmentOnDeviceApp() {
    override fun getOnDeviceApps(): ArrayList<OnDeviceApp> {
        return AppList.sysApps
    }
}
