package me.eagzzycsl.darkroom.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

import me.eagzzycsl.darkroom.model.AppConfigRecord
import me.eagzzycsl.darkroom.model.MetaApp
import java.util.HashMap

object SQLMan {
    private var myDb: SQLiteDatabase? = null
    private fun getDb(context: Context): SQLiteDatabase {
        if (myDb == null) {
            myDb = MySQLiteHelper(context, AboutDb.DB_NAME, null, AboutDb.DB_VERSION).readableDatabase
        }
        return myDb!!
    }

    private val QsReadAll =
            "select * from ${AboutDb.NaughtyTable.TABLE_NAME}"

    fun getAllAppConfig(context: Context): HashMap<String, AppConfigRecord> {
        val c = getDb(context).rawQuery(QsReadAll, arrayOf())
        val appConfigRecords = HashMap<String, AppConfigRecord>(c.count)
        while (c.moveToNext()) {
            val pkgName = c.getString(
                    c.getColumnIndex(AboutDb.NaughtyTable.FILED.pkgName)
            )
            val isNaughty = c.getInt(
                    c.getColumnIndex(AboutDb.NaughtyTable.FILED.isNaughty)
            ) == 1
            appConfigRecords.put(pkgName,AppConfigRecord(
                    pkgName,
                    isNaughty
            ) )
        }
        c.close()
        return appConfigRecords
    }
//    fun readAll(context: Context): ArrayList<MetaApp> {
//        val c = getDb(context).rawQuery(QsReadAll, arrayOf())
//        val naughtyApps = ArrayList<NaughtyApp>(c.count)
//        while (c.moveToNext()) {
//            val pkgNameIndex = c.getColumnIndex(AboutDb.NaughtyTable.FILED.pkgName)
//            val pkgName = c.getString(pkgNameIndex)
//            AppList.addNaughtyAppFromDataBase(pkgName)
//        }
//        c.close()
//        return naughtyApps
//    }


    fun insertNaughtyApps(context: Context, naughtyApps: List<MetaApp>) {
        naughtyApps.forEach({
            val cv = ContentValues()
            cv.put(AboutDb.NaughtyTable.FILED.pkgName, it.pkgName)
            getDb(context).insert(AboutDb.NaughtyTable.TABLE_NAME, null, cv)
        })

    }
    // TODO: 效率太低，可能需要改进或者是加进度条
    fun markNaughtyApps(context: Context, naughtyApps: List<MetaApp>) {
        // 先全部删掉再插入，可以可以避开update操作我不会写的问题和表里面没有pkgName记录的问题
        naughtyApps.forEach({
            getDb(context).execSQL(
                    "delete from ${AboutDb.NaughtyTable.TABLE_NAME} where ${AboutDb.NaughtyTable.FILED.pkgName} = '${it.pkgName}'"
            )
            this.writeAllMetaApps(context, naughtyApps)
        })

    }
    fun writeAllMetaApps(context: Context,metaApps:List<MetaApp>) {
        metaApps.forEach({
            val cv = ContentValues()
            cv.put(AboutDb.NaughtyTable.FILED.pkgName, it.pkgName)
            cv.put(AboutDb.NaughtyTable.FILED.isNaughty, it.isNaughty)
            getDb(context).insert(AboutDb.NaughtyTable.TABLE_NAME, null, cv)
        })
    }

    fun remove(context: Context) {
        getDb(context).delete(AboutDb.NaughtyTable.TABLE_NAME, null, null)
    }

}
