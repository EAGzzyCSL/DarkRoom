package me.eagzzycsl.darkroom.model;

import android.content.Context;

import me.eagzzycsl.darkroom.access.SettingsStateMachineManager;

public class NaughtyApp extends MyApp {


    public NaughtyApp(MetaApp metaApp) {
        super(metaApp);
    }
    public void frozen(Context context){
        SettingsStateMachineManager.getInstance().startResponse();
        SettingsStateMachineManager.getInstance().setCurrentWorkingApp(this);
        this.goToSettings(context);
    }
    public boolean isEnable(Context context){
        return getMetaApp().isEnable(context);
    }
    public void launch(Context context){
        if(isEnable(context)){
            context.startActivity(getAppIntent(context));
        }else{
            this.unFrozen(context);
        }
    }
    public void unFrozen(Context context){
        SettingsStateMachineManager.getInstance().setCurrentWorkingApp(this);
        SettingsStateMachineManager.getInstance().resetEnable();
        SettingsStateMachineManager.getInstance().startResponse();
        this.goToSettings(context);
    }
}
