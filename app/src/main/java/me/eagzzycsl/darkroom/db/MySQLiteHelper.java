package me.eagzzycsl.darkroom.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


class MySQLiteHelper extends SQLiteOpenHelper implements AboutDb {

    MySQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String sql_create =
                "CREATE TABLE IF NOT EXISTS " + TABLE_Naughty.TABLE_NAME + "("
                        + TABLE_Naughty.FILED.ID + " integer primary key autoincrement, "
                        + TABLE_Naughty.FILED.pkgName + " text);";
        db.execSQL(sql_create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}