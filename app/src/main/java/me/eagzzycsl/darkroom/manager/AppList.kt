package me.eagzzycsl.darkroom.manager

import android.content.Context
import android.content.pm.ApplicationInfo

import me.eagzzycsl.darkroom.db.SQLMan
import me.eagzzycsl.darkroom.model.MetaApp
import java.text.Collator
import java.util.*


object AppList {
    private val metaApps = HashMap<String, MetaApp>()
    val naughtyApps = ArrayList<MetaApp>()
    val sysApps = ArrayList<MetaApp>()
    val userApps = ArrayList<MetaApp>()
    val easyFreezeApps = ArrayList<MetaApp>()
    private val appNameCollator = Collator.getInstance(Locale.CHINA)
    fun init(context: Context) {
        // 读取数据库中app的是否冻结记录
        val appConfigRecords  = SQLMan.getAllAppConfig(context)
        // 获取手机上安装的全部app
        // 如果数据库中标记某个app为冻结，那么标记该app为冻结app
        val pm = context.packageManager
        val apps = pm.getInstalledApplications(0)
        apps.filter { it.flags and ApplicationInfo.FLAG_SYSTEM != 1 }.forEach({
            val metaApp = MetaApp(
                    it.loadLabel(pm).toString(),
                    it.packageName,
                    it.loadIcon(pm),
                    appConfigRecords[it.packageName]?.isNaughty?: false,
                    false
            )
            metaApps[it.packageName] = metaApp
        })
        apps.filter { it.flags and ApplicationInfo.FLAG_SYSTEM == 1 }.forEach({
            val metaApp = MetaApp(
                    it.loadLabel(pm).toString(),
                    it.packageName,
                    it.loadIcon(pm),
                    appConfigRecords[it.packageName]?.isNaughty?: false,
                    true
            )
            metaApps[it.packageName] = metaApp
        })
        this.calc()
        // 将新的数据写回数据库
        SQLMan.writeAllMetaApps(context, metaApps.values.toList())
    }
    private fun calc() {
        this.calcNaughtyApps()
        this.calcUserApps()
        this.calcSysApps()
    }
    private fun calcNaughtyApps() {
        this.naughtyApps.clear()
        this.naughtyApps.addAll(
                metaApps.filterValues { it.isNaughty  }.values.toList()
        )
        this.naughtyApps.sortWith(
                kotlin.Comparator { o1, o2 -> appNameCollator.compare(o1.appName, o2.appName) }
        )
    }
    private fun calcUserApps() {
        this.userApps.clear()
        this.userApps.addAll(
                metaApps.values.filter { !it.isSysApp }
        )
        this.userApps.sortWith(
                kotlin.Comparator { o1, o2 -> appNameCollator.compare(o1.appName, o2.appName) }
        )
    }
    private fun calcSysApps() {
        this.sysApps.clear()
        this.sysApps.addAll(
                metaApps.values.filter { it.isSysApp }
        )
        this.sysApps.sortWith(
                kotlin.Comparator { o1, o2 -> appNameCollator.compare(o1.appName, o2.appName) }
        )
    }
    fun genEasyFreezeApps(appName: String) {
        easyFreezeApps.clear()
        easyFreezeApps.addAll(
                metaApps.values.filter { it.appName == appName }
        )
    }

    fun saveNaughtyApps(context: Context) {
        SQLMan.markNaughtyApps(context,
                metaApps.values.filter { it.willFreeze }
        )
        this.calcNaughtyApps()
    }
    fun markFrozen() {
        this.metaApps.values.forEach { it -> it.willFreeze = it.isNaughty }
    }
    // TODO:这样子会导致已经冻结的app还没有被释放
    fun markNaughty() {
        this.metaApps.values.forEach { it-> it.isNaughty = it.willFreeze}
    }
    fun unMarkFrozen() {
        this.metaApps.values.forEach{ it -> it.willFreeze = false }
    }
}
