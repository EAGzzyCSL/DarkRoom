package me.eagzzycsl.darkroom.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

import java.util.ArrayList

import me.eagzzycsl.darkroom.manager.AppList
import me.eagzzycsl.darkroom.model.NaughtyApp


class SQLMan private constructor(context: Context) : AboutDb {
    val Qs_readAll =
            "select " + AboutDb.NaughtyTable.FILED.pkgName + " from " + AboutDb.NaughtyTable.TABLE_NAME;
    var myDb: SQLiteDatabase? =null
    init {
        myDb = MySQLiteHelper(context, AboutDb.Companion.DB_NAME, null, AboutDb.Companion.DB_VERSION).readableDatabase
    }


    fun readAll(): ArrayList<NaughtyApp> {
        val c = myDb!!.rawQuery(Qs_readAll, arrayOf())
        val naughtyApps = ArrayList<NaughtyApp>(c.count)

        while (c.moveToNext()) {
            for (i in 0 until c.columnCount) {
                val pkgNameIndex = c.getColumnIndex(AboutDb.NaughtyTable.FILED.pkgName)
                val pkgName = c.getString(pkgNameIndex)
                val naughtyApp = AppList.getNaughtApp(pkgName)
                // 已经卸载了的app就算了。
                if (naughtyApp != null) {
                    naughtyApps.add(naughtyApp)
                }
            }
        }
        c.close()
        return naughtyApps
    }


    fun insert(naughtyApp: NaughtyApp?) {
        if (naughtyApp == null) {
            return
        }
        val cv = ContentValues()
        cv.put(AboutDb.NaughtyTable.FILED.pkgName, naughtyApp.pkgName)
        myDb!!.insert(AboutDb.NaughtyTable.TABLE_NAME, null, cv)
    }

    fun remove() {
        myDb!!.delete(AboutDb.NaughtyTable.TABLE_NAME, null, null)
    }


    companion object {

        private var instance: SQLMan? = null

        fun getInstance(context: Context): SQLMan? {
            if (instance == null) {
                instance = SQLMan(context)
            }
            return instance
        }
    }
}
