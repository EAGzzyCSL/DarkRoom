package me.eagzzycsl.darkroom.model


class OnDeviceApp(metaApp: MetaApp) : MyApp(metaApp) {

    var frozen: Boolean = false

    fun toNaughtyApp(): NaughtyApp {
        return NaughtyApp(metaApp)
    }

    fun toggleFrozen() {
        this.frozen = !frozen
    }
}
