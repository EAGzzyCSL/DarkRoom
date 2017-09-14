package me.eagzzycsl.darkroom.access;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.eagzzycsl.darkroom.db.SQLMan;
import me.eagzzycsl.darkroom.model.MetaApp;
import me.eagzzycsl.darkroom.model.NaughtyApp;
import me.eagzzycsl.darkroom.model.OnDeviceApp;


public final class AppList {
    private final static ArrayList<NaughtyApp> naughtyApps = new ArrayList<>();
    private final static ArrayList<OnDeviceApp> sysApps = new ArrayList<>();
    private final static ArrayList<OnDeviceApp> userApps = new ArrayList<>();
    private final static HashMap<String, MetaApp> apps = new HashMap<>();

    public static void init(Context context) {
        getOnDeviceApp(context);
        naughtyApps.clear();
        naughtyApps.addAll(SQLMan.getInstance(context).readAll());

    }

    public static MetaApp getMetaApp(String pkgName) {
        return apps.get(pkgName);
    }

    public static NaughtyApp getNaughtApp(String pkgName) {
        final MetaApp metaApp = getMetaApp(pkgName);
        return metaApp == null ? null : new NaughtyApp(metaApp);
    }

    public static void addMetaApp(MetaApp metaApp) {
        final String pkgName = metaApp.getPkgName();
        apps.putIfAbsent(pkgName, metaApp);
    }


    public static ArrayList<OnDeviceApp> getSysApps() {
        return sysApps;
    }

    public static ArrayList<OnDeviceApp> getUserApps() {
        return userApps;
    }

    public static ArrayList<NaughtyApp> getNaughtyApps() {
        return naughtyApps;
    }

    public static void clearNaughtyApps() {
        naughtyApps.clear();
    }

    public static void addNaughtyApp(NaughtyApp naughtyApp) {
        naughtyApps.add(naughtyApp);
    }

    public static boolean appInNaughty(OnDeviceApp onDeviceApp) {
        return naughtyApps.stream().anyMatch(naughtyApp ->
                naughtyApp.getMetaApp() == onDeviceApp.getMetaApp()
        );
    }

    public static void getOnDeviceApp(Context context) {
        userApps.clear();
        sysApps.clear();
        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> apps = pm.getInstalledApplications(0);
        for (ApplicationInfo app : apps) {
            // updated system app
            if ((app.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
                //system app
            } else if ((app.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                addToList(sysApps, app, pm);
                // user-installed app
            } else {
                addToList(userApps, app, pm);

            }
        }
    }

    public static void addToList(
            ArrayList<OnDeviceApp> apps,
            ApplicationInfo app,
            PackageManager pm
    ) {
        String pkgName = app.packageName;

        MetaApp metaApp = AppList.getMetaApp(pkgName) == null ? new MetaApp(
                app.loadLabel(pm).toString(),
                pkgName,
                app.loadIcon(pm)
        ) : AppList.getMetaApp(pkgName);
        AppList.addMetaApp(metaApp);
        apps.add(new OnDeviceApp(metaApp));
    }

}
