package me.eagzzycsl.darkroom.db;


interface AboutDb {
    String DB_NAME = "DarkRoom";
    int DB_VERSION = 1;

    interface TABLE_Naughty {
        String TABLE_NAME = "naughty";

        interface FILED {
            String ID = "_ID";
            String pkgName = "pkgName";
        }
    }
}
