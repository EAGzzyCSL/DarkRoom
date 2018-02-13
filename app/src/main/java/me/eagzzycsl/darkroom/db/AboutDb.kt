package me.eagzzycsl.darkroom.db


object AboutDb {

    object NaughtyTable {

        object FILED {
            const val ID = "_ID"
            const val pkgName = "pkgName"
            const val isNaughty = "isNaughty"
            const val freezeNotifyTimes = "freezeNotifyTimes"
        }

        const val TABLE_NAME = "naughty"
    }

    const val DB_NAME = "DarkRoom"
    const val DB_VERSION = 2
}
