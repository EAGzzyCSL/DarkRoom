package me.eagzzycsl.darkroom.ui;

import android.os.Bundle;

import java.util.ArrayList;

import me.eagzzycsl.darkroom.access.AppList;
import me.eagzzycsl.darkroom.db.SQLMan;
import me.eagzzycsl.darkroom.model.MyApp;
import me.eagzzycsl.darkroom.model.NaughtyApp;
import me.eagzzycsl.darkroom.model.OnDeviceApp;


public abstract class FragmentOnDeviceApp<O extends MyApp> extends BaseFragment<OnDeviceApp> {

    private ArrayList<OnDeviceApp> onDeviceApps = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onDeviceApps = getOnDeviceApps();
        this.markFrozen();
        setData(onDeviceApps);
    }

    public abstract ArrayList<OnDeviceApp> getOnDeviceApps();

    private void markFrozen() {
        onDeviceApps.forEach(onDeviceApp -> {
            (onDeviceApp).setFrozen(
                    AppList.appInNaughty(onDeviceApp)
            );
        });
    }


    public void savePick() {
        onDeviceApps.forEach(onDeviceApp -> {
            if (onDeviceApp.getFrozen()) {
                final NaughtyApp naughtyApp = onDeviceApp.toNaughtyApp();
                SQLMan.getInstance(getContext()).insert(naughtyApp);
                AppList.addNaughtyApp(naughtyApp);
            }
        });
    }

    @Override
    public AdapterOnDeviceApp getAdapter() {
        return new AdapterOnDeviceApp(getActivity());
    }
}
