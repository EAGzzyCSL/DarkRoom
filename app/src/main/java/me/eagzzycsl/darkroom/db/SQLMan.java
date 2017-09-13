package me.eagzzycsl.darkroom.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import me.eagzzycsl.darkroom.access.AppList;
import me.eagzzycsl.darkroom.model.NaughtyApp;


public class SQLMan implements AboutDb {
    private static SQLiteDatabase myDb;

    private SQLMan(Context context) {
        myDb = new MySQLiteHelper(context, DB_NAME,
                null, DB_VERSION).getReadableDatabase();
    }

    private static SQLMan instance;

    public static SQLMan getInstance(Context context) {
        if (instance == null) {
            instance = new SQLMan(context);
        }
        return instance;
    }


    private final static String Qs_readAll =
            "select " + TABLE_Naughty.FILED.pkgName + " from " + TABLE_Naughty.TABLE_NAME;


    public ArrayList<NaughtyApp> readAll() {
        Cursor c = myDb.rawQuery(Qs_readAll, new String[]{});
        final ArrayList<NaughtyApp> naughtyApps = new ArrayList<>(c.getCount());

        while (c.moveToNext()) {
            for (int i = 0; i < c.getColumnCount(); i++) {
                final int pkgNameIndex = c.getColumnIndex(TABLE_Naughty.FILED.pkgName);
                final String pkgName = c.getString(pkgNameIndex);
                final NaughtyApp naughtyApp = AppList.getNaughtApp(pkgName);
                // 已经卸载了的app就算了。
                if (naughtyApp != null) {
                    naughtyApps.add(naughtyApp);
                }
            }
        }
        c.close();
        return naughtyApps;
    }


    public void insert(NaughtyApp naughtyApp) {
        if (naughtyApp == null) {
            return;
        }
        ContentValues cv = new ContentValues();
        cv.put(TABLE_Naughty.FILED.pkgName, naughtyApp.getPkgName());
        myDb.insert(TABLE_Naughty.TABLE_NAME, null, cv);
    }

    public void remove() {
        myDb.delete(TABLE_Naughty.TABLE_NAME, null, null);
    }
}
