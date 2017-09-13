package me.eagzzycsl.darkroom.ui;

import android.os.Bundle;

import java.util.ArrayList;

import me.eagzzycsl.darkroom.access.AppList;
import me.eagzzycsl.darkroom.model.NaughtyApp;

public class FragmentNaughty extends BaseFragment<NaughtyApp> {
    private ArrayList<NaughtyApp> naughtyApps;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        naughtyApps = AppList.getNaughtyApps();
        setData(naughtyApps);
    }

    public ArrayList<NaughtyApp> getData() {
        return naughtyApps;
    }

    @Override
    public AdapterNaughty getAdapter() {

        return new AdapterNaughty(getActivity());
    }

}
