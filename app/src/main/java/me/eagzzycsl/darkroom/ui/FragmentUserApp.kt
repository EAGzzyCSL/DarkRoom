package me.eagzzycsl.darkroom.ui

import java.util.ArrayList

import me.eagzzycsl.darkroom.manager.AppList
import me.eagzzycsl.darkroom.model.OnDeviceApp


class FragmentUserApp : FragmentOnDeviceApp<OnDeviceApp>() {
    override fun getOnDeviceApps(): ArrayList<OnDeviceApp> {
        return AppList.userApps
    }

}
