package me.eagzzycsl.darkroom;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import me.eagzzycsl.darkroom.access.AppList;
import me.eagzzycsl.darkroom.access.FreezeTask;
import me.eagzzycsl.darkroom.model.NaughtyApp;
import me.eagzzycsl.darkroom.ui.ActivityToolbar;
import me.eagzzycsl.darkroom.ui.FragmentNaughty;
import me.eagzzycsl.darkroom.uitls.MyConfig;
import me.eagzzycsl.darkroom.uitls.MyLogger;

public class MainActivity extends ActivityToolbar implements View.OnClickListener {
    private ArrayList<NaughtyApp> naughtyApps;
    private FragmentNaughty naughtyFragment;
    private FloatingActionButton fab_add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myFind();
        myRead();
        myCreate();
        mySet();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    private void myFind() {
        fab_add = (FloatingActionButton) findViewById(R.id.fab_add);
        naughtyFragment = (FragmentNaughty) getSupportFragmentManager().findFragmentById(R.id.fragment_naughty);

    }

    private void myRead() {
        AppList.init(this);
    }

    private void myCreate() {

    }

    private void mySet() {
        fab_add.setOnClickListener(this);
        if(MyConfig.DEBUG && MyConfig.NO_CHECK_ACCESS){
            return;
        }
        requireAccessibility();
    }

    private void requireAccessibility() {
        if (!this.isAccessibilitySettingsOn(this)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("无障碍权限未授予，程序正常工作需要该权限，是否立即授予？")
                    .setNegativeButton("否", (dialogInterface, i) -> {
                        this.finish();
                        dialogInterface.dismiss();
                    })
                    .setPositiveButton("是", (dialogInterface, i) -> {
                        this.startAccessibility();
                        dialogInterface.dismiss();
                    })
                    .create().show();
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_add: {
                naughtyApps = naughtyFragment.getData();
                final FreezeTask freezeTask = FreezeTask.getInstance();
                freezeTask.init();
                naughtyApps.forEach(naughtyApp -> {
                    if (naughtyApp.isEnable(this)) {
                        freezeTask.addTask(naughtyApp);
                    }
                });
                freezeTask.exec(this);
                break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit: {
                startActivity(new Intent(
                        this,
                        NaughtyPickActivity.class
                ));
                break;
            }
            case R.id.action_backup_restore: {
                startActivity(new Intent(
                        this,
                        BackRestoreActivity.class
                ));
                break;
            }
            case R.id.action_about: {
                startActivity(new Intent(
                        this,
                        AboutActivity.class
                ));
                break;
            }
        }
        return true;
    }

    private boolean isAccessibilitySettingsOn(Context context) {
        int accessibilityEnabled = 0;
        try {
            accessibilityEnabled = Settings.Secure.getInt(context.getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            MyLogger.exception.i("获取access状态失败");
        }

        if (accessibilityEnabled == 1) {
            String services = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (services != null) {
                return services.toLowerCase().contains(context.getPackageName().toLowerCase());
            }
        }

        return false;
    }

    private void startAccessibility() {
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        startActivity(intent);
    }

}
