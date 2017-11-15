package me.eagzzycsl.darkroom.access;

import me.eagzzycsl.darkroom.model.NaughtyApp;
import me.eagzzycsl.darkroom.uitls.MyLogger;


public class SettingsStateMachineManager {
    private NaughtyApp currentWorkingApp = null;
    private final MachineState state_init = new MachineState("init");
    private final MachineState state_maybeNone = new MachineState("maybeNone");
    private final MachineState state_showAppDetail = new MachineState("showAppDetail");
    private final MachineState state_showConfirm = new MachineState("showConfirm");
    private final MachineState state_afterConfirm = new MachineState("afterConfirm");
    private MachineState currentState = state_init;
    private static SettingsStateMachineManager singleInstance = new SettingsStateMachineManager();
    private boolean inChange = false;
    private final int workingMode_disable = 0;
    private final int workingMode_enable = 1;
    // TODO: working mode
    private int currentWorkingMode = workingMode_disable;

    public static SettingsStateMachineManager getInstance() {
        return singleInstance;
    }

    {
        state_init.setNext(ActivityChange.None, state_maybeNone);
        state_init.setNext(ActivityChange.appDetail, state_showAppDetail);

        state_maybeNone.setNext(ActivityChange.appDetail, state_showAppDetail);

        state_showAppDetail.setNext(ActivityChange.None, state_showConfirm);

        state_showConfirm.setNext(ActivityChange.appDetail, state_afterConfirm);
    }

    private SettingsStateMachineManager() {

    }

    public boolean readyToDisable() {
        return this.currentState == state_showAppDetail;
    }

    public boolean readyToEnable() {
        return this.currentState == state_showAppDetail;
    }

    public boolean readyToConfirm() {
        return this.currentState == state_showConfirm;
    }

    public boolean readyToNext() {
        return this.currentState == state_afterConfirm;
    }

    public void accept(ActivityChange change) {
        if (this.inChange) {
            if (this.currentState == null) {
                MyLogger.exception.i("异常的下一个状态在状态机中");
            } else {
                this.currentState = this.currentState.accept(change);
            }
        }
    }

    public boolean isReset() {
        return this.currentState == state_init && !inChange;
    }

    private void reset() {
        this.currentState = state_init;
        this.inChange = false;
    }

    public void resetEnable() {
        this.reset();
        this.currentWorkingMode = workingMode_enable;
    }

    public void resetDisable() {
        this.reset();
        this.currentWorkingMode = workingMode_disable;
    }

    public boolean isWorkingInEnable() {
        return this.currentWorkingMode == workingMode_enable;

    }

    public boolean isWorkingInDisable() {
        return this.currentWorkingMode == workingMode_disable;

    }

    public void startResponse() {
        this.inChange = true;
    }

    public void setCurrentWorkingApp(NaughtyApp naughtyApp) {
        this.currentWorkingApp = naughtyApp;
    }

    public NaughtyApp getCurrentWorkingApp() {
        return currentWorkingApp;
    }
}
