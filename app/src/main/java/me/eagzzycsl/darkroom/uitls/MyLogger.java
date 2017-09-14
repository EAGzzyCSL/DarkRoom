package me.eagzzycsl.darkroom.uitls;

import android.util.Log;

public enum MyLogger {

    accessibility("无障碍服务"),
    exception("异常"),
    freeze("冻结任务");

    public String name;

    MyLogger(String name) {
        this.name = name;
    }

    private void i(String s) {
        Log.i(this.name, s);
    }


    private String stringify(Object object) {
        String type = object == null ? "null" :
                object instanceof String ? "String" : "Object";
        String log = object == null ? "null" : object.toString();

        return type + " | " + log;
    }

    public void i(Object object) {
        if (MyConfig.DEBUG) {
            this.i(this.stringify(object));
        }
    }

    public void i(String tag, Object object) {
        if (MyConfig.DEBUG) {
            this.i(tag + "@: " + this.stringify(object));
        }
    }
}
