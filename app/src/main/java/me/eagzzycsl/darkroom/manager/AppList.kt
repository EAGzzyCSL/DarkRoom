package me.eagzzycsl.darkroom.manager

import android.content.Context
import android.content.pm.ApplicationInfo

import java.util.ArrayList
import java.util.HashMap

import me.eagzzycsl.darkroom.db.SQLMan
import me.eagzzycsl.darkroom.model.MetaApp
import me.eagzzycsl.darkroom.model.NaughtyApp
import me.eagzzycsl.darkroom.model.OnDeviceApp


object AppList {
    private val metaApps = HashMap<String, MetaApp>()
    val naughtyApps = ArrayList<NaughtyApp>()
    val sysApps = ArrayList<OnDeviceApp>()
    val userApps = ArrayList<OnDeviceApp>()
    val easyFreezeApps = ArrayList<OnDeviceApp>()

    fun genEasyFreezeApps(appName: String) {
        easyFreezeApps.clear()
        easyFreezeApps.addAll(
                metaApps.filter { it.value.appName == appName }.map { OnDeviceApp(it.value) }
        )
    }

    fun init(context: Context) {
        initOnDeviceApp(context)
        naughtyApps.clear()
        naughtyApps.addAll(SQLMan.readAll(context))
    }

    private fun initOnDeviceApp(context: Context) {
        userApps.clear()
        sysApps.clear()
        val pm = context.packageManager
        val apps = pm.getInstalledApplications(0)
        for (app in apps) {
            val metaApp = MetaApp(
                    app.loadLabel(pm).toString(),
                    app.packageName,
                    app.loadIcon(pm)
            )
            metaApps.put(app.packageName, metaApp)
            when {
//                app.flags and ApplicationInfo.FLAG_UPDATED_SYSTEM_APP != 0 ||
                app.flags and ApplicationInfo.FLAG_SYSTEM != 0
                -> {
                    sysApps.add(OnDeviceApp(metaApp))
                }
                else -> {

                    userApps.add(OnDeviceApp(metaApp))
                }
            }
        }
    }

    fun addNaughtyAppFromDataBase(pkgName: String) {
        // 如果未安装就不展示(新安装的app)
        val naughtyApp = createNaughtAppFromPkgName(pkgName)
        if (naughtyApp != null) {
            naughtyApps.add(naughtyApp)
        }
    }

    fun createNaughtAppFromPkgName(pkgName: String): NaughtyApp? {
        val metaApp = metaApps[pkgName]
        return if (metaApp == null) null else NaughtyApp(metaApp)
    }

    fun saveNaughtyApps(context: Context, naughtyAppToAdd: List<NaughtyApp>) {
        SQLMan.remove(context)
        this.naughtyApps.clear()
        AppList.naughtyApps.addAll(naughtyAppToAdd)
        SQLMan.insertNaughtyApps(context, this.naughtyApps)
    }

    fun appendNaughtyApps(context: Context, naughtyAppToAdd: List<NaughtyApp>) {
        AppList.naughtyApps.addAll(naughtyAppToAdd)
        SQLMan.insertNaughtyApps(context, this.naughtyApps)
    }

    fun isNaughtyApp(onDeviceApp: OnDeviceApp): Boolean {
        return naughtyApps.any { it -> it.metaApp == onDeviceApp.metaApp }
    }


}
