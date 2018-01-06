package me.eagzzycsl.darkroom.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class MySQLiteHelper(context: Context, name: String, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase) {
        val sqlCreate = (
                "CREATE TABLE IF NOT EXISTS ${AboutDb.NaughtyTable.TABLE_NAME}("
                        + "${AboutDb.NaughtyTable.FILED.ID} integer primary key autoincrement, "
                        + "${AboutDb.NaughtyTable.FILED.pkgName}  text);")
        db.execSQL(sqlCreate)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }

}