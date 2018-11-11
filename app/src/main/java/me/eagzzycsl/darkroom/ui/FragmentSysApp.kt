package me.eagzzycsl.darkroom.ui

import java.util.ArrayList

import me.eagzzycsl.darkroom.manager.AppList
import me.eagzzycsl.darkroom.model.MetaApp


class FragmentSysApp : FragmentOnDeviceApp() {
    override fun getOnDeviceApps(): ArrayList<MetaApp> {
        return AppList.sysApps
    }
}
