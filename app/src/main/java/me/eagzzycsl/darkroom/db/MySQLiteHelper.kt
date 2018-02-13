package me.eagzzycsl.darkroom.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class MySQLiteHelper(context: Context, name: String, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase) {
        val sqlCreate = (
                "CREATE TABLE IF NOT EXISTS ${AboutDb.NaughtyTable.TABLE_NAME}("
                        + "${AboutDb.NaughtyTable.FILED.ID} integer primary key autoincrement, "
                        + "${AboutDb.NaughtyTable.FILED.isNaughty} integer DEFAULT 1, "
                        + "${AboutDb.NaughtyTable.FILED.freezeNotifyTimes} integer DEFAULT 0, "
                        + "${AboutDb.NaughtyTable.FILED.pkgName}  text);")
        db.execSQL(sqlCreate)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion == 1) {
            db.execSQL(
                    "alter table ${AboutDb.NaughtyTable.TABLE_NAME} add column "
                            + "${AboutDb.NaughtyTable.FILED.isNaughty} integer DEFAULT 1"
            )
            db.execSQL(
                    "alter table ${AboutDb.NaughtyTable.TABLE_NAME} add column "
                            + "${AboutDb.NaughtyTable.FILED.freezeNotifyTimes} integer DEFAULT 0"
            )

        }
    }

}