package me.eagzzycsl.darkroom.ui;

import android.os.Bundle;

import java.util.ArrayList;

import me.eagzzycsl.darkroom.access.AppList;
import me.eagzzycsl.darkroom.db.SQLMan;
import me.eagzzycsl.darkroom.model.NaughtyApp;
import me.eagzzycsl.darkroom.model.UserApp;


public class FragmentUserApp extends BaseFragment<UserApp> {

    private ArrayList<UserApp> userApps;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userApps = AppList.getUserApps();
        this.markFrozen();
        setData(userApps);
    }

    private void markFrozen() {
        userApps.forEach(userApp -> {
            userApp.setFrozen(
                    AppList.useAppInNaughty(userApp)
            );
        });
    }

    public void savePick() {
        SQLMan.getInstance(getContext()).remove();
        AppList.clearNaughtyApps();
        userApps.forEach(userApp -> {
            if (userApp.getFrozen()) {
                final NaughtyApp naughtyApp = userApp.toNaughtyApp();
                SQLMan.getInstance(getContext()).insert(naughtyApp);
                AppList.addNaughtyApp(naughtyApp);
            }
        });
    }

    @Override
    public AdapterUserApp getAdapter() {
        return new AdapterUserApp(getActivity());
    }
}
