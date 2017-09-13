package me.eagzzycsl.darkroom.access;

import me.eagzzycsl.darkroom.uitls.ConstantString;

public enum ActivityChange {
    // 注意初始状态字符串不要重复了
    init("~"),
    appDetail(ConstantString.setting_detail_activity_name),
    None(""),
    illegal(null);
    private String activityName;

    ActivityChange(String activityName) {
        this.activityName = activityName;
    }

    public boolean match(String name) {
        return this != illegal && this.activityName.equals(name);
    }

    public static ActivityChange getState(String name) {
        for (ActivityChange m : ActivityChange.values()) {
            if (m.match(name)) {
                return m;
            }
        }
        return illegal;
    }
}
