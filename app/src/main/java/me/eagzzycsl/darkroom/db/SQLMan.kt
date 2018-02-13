package me.eagzzycsl.darkroom.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

import java.util.ArrayList

import me.eagzzycsl.darkroom.manager.AppList
import me.eagzzycsl.darkroom.model.NaughtyApp

object SQLMan {
    private var myDb: SQLiteDatabase? = null
    private fun getDb(context: Context): SQLiteDatabase {
        if (myDb == null) {
            myDb = MySQLiteHelper(context, AboutDb.DB_NAME, null, AboutDb.DB_VERSION).readableDatabase
        }
        return myDb!!
    }

    private val QsReadAll =
            "select ${AboutDb.NaughtyTable.FILED.pkgName} from ${AboutDb.NaughtyTable.TABLE_NAME}"

    fun readAll(context: Context): ArrayList<NaughtyApp> {
        val c = getDb(context).rawQuery(QsReadAll, arrayOf())
        val naughtyApps = ArrayList<NaughtyApp>(c.count)
        while (c.moveToNext()) {
            val pkgNameIndex = c.getColumnIndex(AboutDb.NaughtyTable.FILED.pkgName)
            val pkgName = c.getString(pkgNameIndex)
            AppList.addNaughtyAppFromDataBase(pkgName)
        }
        c.close()
        return naughtyApps
    }


    fun insertNaughtyApps(context: Context, naughtyApps: List<NaughtyApp>) {
        naughtyApps.forEach({
            val cv = ContentValues()
            cv.put(AboutDb.NaughtyTable.FILED.pkgName, it.pkgName)
            getDb(context).insert(AboutDb.NaughtyTable.TABLE_NAME, null, cv)
        })

    }

    fun remove(context: Context) {
        getDb(context).delete(AboutDb.NaughtyTable.TABLE_NAME, null, null)
    }
}
