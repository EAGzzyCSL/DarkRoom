package me.eagzzycsl.darkroom;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import me.eagzzycsl.darkroom.ui.ActivityToolbar;
import me.eagzzycsl.darkroom.ui.FragmentUserApp;

public class NaughtyPickActivity extends ActivityToolbar implements View.OnClickListener {
    private FragmentUserApp userAppFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userAppFragment = (FragmentUserApp) getFragmentManager().findFragmentById(R.id.fragment_user);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_naughty_pick;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab: {
                userAppFragment.savePick();
                finish();
                break;

            }
        }
    }
}
