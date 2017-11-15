package me.eagzzycsl.darkroom.model;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import me.eagzzycsl.darkroom.JumpActivity;
import me.eagzzycsl.darkroom.access.SettingsStateMachineManager;
import me.eagzzycsl.darkroom.utils.ConstantString;

public class NaughtyApp extends MyApp {


    public NaughtyApp(MetaApp metaApp) {
        super(metaApp);
    }

    public void frozen(Context context) {
        SettingsStateMachineManager.getInstance().startResponse();
        SettingsStateMachineManager.getInstance().setCurrentWorkingApp(this);
        this.goToSettings(context);
    }

    public boolean isEnable(Context context) {
        return getMetaApp().isEnable(context);
    }

    public void launch(Context context) {
        if (isEnable(context)) {
            context.startActivity(getAppIntent(context));
        } else {
            this.unFrozen(context);
        }
    }

    public void unFrozen(Context context) {
        SettingsStateMachineManager.getInstance().setCurrentWorkingApp(this);
        SettingsStateMachineManager.getInstance().resetEnable();
        SettingsStateMachineManager.getInstance().startResponse();
        this.goToSettings(context);
    }

    private static final String ACTION_ADD_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";

    public void createShortcut(Context context) {
        Intent addShortcutIntent = new Intent(ACTION_ADD_SHORTCUT);
        addShortcutIntent.putExtra("duplicate", true);
        addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getAppName());
        // 快捷方式的图标
        addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON, getIconBitmap(
                context,
                getAppIcon()
        ));
        Intent actionIntent = new Intent(
                context,
                JumpActivity.class
        );
        actionIntent.putExtra(ConstantString.pkgName, getPkgName());
        addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, actionIntent);
        context.sendBroadcast(addShortcutIntent);
        Toast.makeText(context, "快捷方式创建成功", Toast.LENGTH_SHORT).show();
    }

    private Bitmap getIconBitmap(Context context, Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
