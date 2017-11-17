package me.eagzzycsl.darkroom.db


internal interface AboutDb {

    interface NaughtyTable {

        interface FILED {
            companion object {
                val ID = "_ID"
                val pkgName = "pkgName"
            }
        }

        companion object {
            val TABLE_NAME = "naughty"
        }
    }

    companion object {
        val DB_NAME = "DarkRoom"
        val DB_VERSION = 1
    }
}
