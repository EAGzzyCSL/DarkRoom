package me.eagzzycsl.darkroom.access;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.eagzzycsl.darkroom.db.SQLMan;
import me.eagzzycsl.darkroom.model.MetaApp;
import me.eagzzycsl.darkroom.model.NaughtyApp;
import me.eagzzycsl.darkroom.model.UserApp;


public final class AppList {
    private final static ArrayList<NaughtyApp> naughtyApps = new ArrayList<>();
    private final static ArrayList<UserApp> userApps = new ArrayList<>();
    private final static HashMap<String, MetaApp> apps = new HashMap<>();

    public static void init(Context context) {
        getAllUserApp(context);
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


    public static ArrayList<UserApp> getUserApps() {
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

    public static boolean useAppInNaughty(UserApp userApp) {
        return naughtyApps.stream().anyMatch(naughtyApp ->
                naughtyApp.getMetaApp() == userApp.getMetaApp()
        );
    }

    public static ArrayList<UserApp> getAllUserApp(Context context) {
        userApps.clear();
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> packages = pm.getInstalledPackages(0);
        // 判断系统/非系统应用
        packages.stream()
                .filter(packageInfo -> (
                        packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0
                ).
                forEach(packageInfo -> {
                    String pkgName = packageInfo.packageName;
                    MetaApp metaApp = AppList.getMetaApp(pkgName) == null ? new MetaApp(
                            packageInfo.applicationInfo.loadLabel(pm).toString(),
                            packageInfo.packageName,
                            packageInfo.applicationInfo.loadIcon(pm)
                    ) : AppList.getMetaApp(pkgName);
                    AppList.addMetaApp(metaApp);
                    userApps.add(new UserApp(metaApp));
                });
        return userApps;
    }
}
