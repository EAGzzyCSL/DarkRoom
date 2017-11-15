package me.eagzzycsl.darkroom.access;


import java.util.HashMap;


public class MachineState {
    private final HashMap<ActivityChange, MachineState> next = new HashMap<>();
    private final String name;
    public MachineState(String name) {
        this.name =name;
    }
    public String getName(){
        return this.name;
    }
    public void setNext(ActivityChange activityChange, MachineState machineState) {
        this.next.put(activityChange, machineState);
    }

    public MachineState accept(ActivityChange activityChange) {
        return this.next.get(activityChange);
    }
}
