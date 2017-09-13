package me.eagzzycsl.darkroom.model;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.Settings;

import me.eagzzycsl.darkroom.uitls.ConstantString;

import static android.content.pm.PackageManager.COMPONENT_ENABLED_STATE_DEFAULT;


public class MetaApp {
    final String appName;
    final String pkgName;
    final Drawable appIcon;

    public MetaApp(
            String appName,
            String pkgName,
            Drawable appIcon
    ) {
        this.appName = appName;
        this.pkgName = pkgName;
        this.appIcon = appIcon;
    }

    public String getAppName() {
        return appName;
    }

    public String getPkgName() {
        return pkgName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public Intent getAppIntent(Context context) {
        return context.getPackageManager().getLaunchIntentForPackage(pkgName);
    }

    public void goToSettings(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts(ConstantString._package, getPkgName(), null);
        intent.setData(uri);
        context.startActivity(intent);
    }

    public boolean isEnable(Context context) {
        return context.
                getPackageManager().
                getApplicationEnabledSetting(this.getPkgName())
                == COMPONENT_ENABLED_STATE_DEFAULT;
    }

}
