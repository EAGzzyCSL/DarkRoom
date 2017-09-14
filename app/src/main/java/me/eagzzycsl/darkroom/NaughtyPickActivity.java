package me.eagzzycsl.darkroom;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import me.eagzzycsl.darkroom.access.AppList;
import me.eagzzycsl.darkroom.db.SQLMan;
import me.eagzzycsl.darkroom.ui.ActivityToolbar;
import me.eagzzycsl.darkroom.ui.FragmentOnDeviceApp;
import me.eagzzycsl.darkroom.ui.FragmentSysApp;
import me.eagzzycsl.darkroom.ui.FragmentUserApp;
import me.eagzzycsl.darkroom.ui.PickPagerAdapter;

public class NaughtyPickActivity extends ActivityToolbar implements View.OnClickListener {
    private ViewPager pick_viewPager;
    private TabLayout pick_tab;
    private FragmentSysApp fragmentSysApp = new FragmentSysApp();
    private FragmentUserApp fragmentUserApp = new FragmentUserApp();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pick_tab = (TabLayout) findViewById(R.id.pick_tab);
        pick_viewPager = (ViewPager) findViewById(R.id.pick_pager);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        pick_tab.setupWithViewPager(pick_viewPager);
        pick_viewPager.setAdapter(
                new PickPagerAdapter(
                        getSupportFragmentManager(),
                        new FragmentOnDeviceApp[]{
                                fragmentUserApp,
                                fragmentSysApp,
                        }
                )
        );
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_naughty_pick;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab: {
                SQLMan.getInstance(this).remove();
                AppList.clearNaughtyApps();
                fragmentUserApp.savePick();
                fragmentSysApp.savePick();
                finish();
                break;
            }
        }
    }
}
