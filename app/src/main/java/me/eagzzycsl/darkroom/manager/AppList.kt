package me.eagzzycsl.darkroom.manager

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager

import java.util.ArrayList
import java.util.HashMap

import me.eagzzycsl.darkroom.db.SQLMan
import me.eagzzycsl.darkroom.model.MetaApp
import me.eagzzycsl.darkroom.model.NaughtyApp
import me.eagzzycsl.darkroom.model.OnDeviceApp


object AppList {
    val naughtyApps = ArrayList<NaughtyApp>()
    val sysApps = ArrayList<OnDeviceApp>()
    val userApps = ArrayList<OnDeviceApp>()
    private val apps = HashMap<String, MetaApp>()

    fun init(context: Context) {
        getOnDeviceApp(context)
        naughtyApps.clear()
        naughtyApps.addAll(SQLMan.getInstance(context)!!.readAll())

    }

    fun getMetaApp(pkgName: String): MetaApp? {
        return apps[pkgName]
    }

    fun getNaughtApp(pkgName: String): NaughtyApp? {
        val metaApp = getMetaApp(pkgName)
        return if (metaApp == null) null else NaughtyApp(metaApp)
    }

    fun addMetaApp(metaApp: MetaApp?) {
        val pkgName = metaApp!!.pkgName
        (apps as java.util.Map<String, MetaApp>).putIfAbsent(pkgName, metaApp)
    }

    fun clearNaughtyApps() {
        naughtyApps.clear()
    }

    fun addNaughtyApp(naughtyApp: NaughtyApp) {
        naughtyApps.add(naughtyApp)
    }

    fun appInNaughty(onDeviceApp: OnDeviceApp): Boolean {
        return naughtyApps.stream().anyMatch { naughtyApp -> naughtyApp.metaApp == onDeviceApp.metaApp }
    }

    fun getOnDeviceApp(context: Context) {
        userApps.clear()
        sysApps.clear()
        val pm = context.packageManager
        val apps = pm.getInstalledApplications(0)
        for (app in apps) {
            // updated system app
            if (app.flags and ApplicationInfo.FLAG_UPDATED_SYSTEM_APP != 0) {
                //system app
            } else if (app.flags and ApplicationInfo.FLAG_SYSTEM != 0) {
                addToList(sysApps, app, pm)
                // user-installed app
            } else {
                addToList(userApps, app, pm)

            }
        }
    }

    fun addToList(
            apps: ArrayList<OnDeviceApp>,
            app: ApplicationInfo,
            pm: PackageManager
    ) {
        val pkgName = app.packageName

        val metaApp = if (getMetaApp(pkgName) == null)
            MetaApp(
                    app.loadLabel(pm).toString(),
                    pkgName,
                    app.loadIcon(pm)
            )
        else
            getMetaApp(pkgName)
        addMetaApp(metaApp)
        apps.add(OnDeviceApp(metaApp!!))
    }

}
