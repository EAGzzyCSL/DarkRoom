package me.eagzzycsl.darkroom.ui;

import java.util.ArrayList;

import me.eagzzycsl.darkroom.access.AppList;
import me.eagzzycsl.darkroom.model.OnDeviceApp;


public class FragmentSysApp extends FragmentOnDeviceApp<OnDeviceApp> {
    @Override
    public ArrayList<OnDeviceApp> getOnDeviceApps() {
        return AppList.getSysApps();
    }

}
