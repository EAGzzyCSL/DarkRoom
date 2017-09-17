package me.eagzzycsl.darkroom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import me.eagzzycsl.darkroom.access.AppList;
import me.eagzzycsl.darkroom.model.NaughtyApp;
import me.eagzzycsl.darkroom.uitls.ConstantString;

public class JumpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jump);
        Intent intent = getIntent();
        String pkgName = intent.getStringExtra(ConstantString.pkgName);
        NaughtyApp naughtyApp = AppList.getNaughtApp(pkgName);
        if (naughtyApp != null) {
            naughtyApp.launch(this);
        }
        finish();
    }


}
