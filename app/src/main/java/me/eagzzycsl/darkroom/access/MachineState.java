package me.eagzzycsl.darkroom.access;

import android.transition.ChangeScroll;

import java.util.HashMap;


public class MachineState {
    private final HashMap<ActivityChange, MachineState> next = new HashMap<>();

    public void setNext(ActivityChange activityChange, MachineState machineState) {
        this.next.put(activityChange, machineState);
    }

    public MachineState accept(ActivityChange activityChange) {
        return this.next.get(activityChange);
    }
}
