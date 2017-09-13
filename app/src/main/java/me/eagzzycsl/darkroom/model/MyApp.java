package me.eagzzycsl.darkroom.model;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;


public abstract class MyApp {
    private final MetaApp metaApp;

    public MyApp(MetaApp metaApp) {
        this.metaApp = metaApp;
    }

    public String getAppName() {
        return metaApp.appName;
    }

    public String getPkgName() {
        return metaApp.pkgName;
    }

    public Drawable getAppIcon() {
        return metaApp.appIcon;
    }

    public Intent getAppIntent(Context context) {
        return metaApp.getAppIntent(context);
    }

    public MetaApp getMetaApp() {
        return metaApp;
    }

    public void goToSettings(Context context) {
        metaApp.goToSettings(context);
    }

}
