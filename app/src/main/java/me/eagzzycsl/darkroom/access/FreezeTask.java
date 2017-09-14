package me.eagzzycsl.darkroom.access;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;

import me.eagzzycsl.darkroom.model.NaughtyApp;
import me.eagzzycsl.darkroom.uitls.MyLogger;


public class FreezeTask {
    private static FreezeTask singleInstance;

    private FreezeTask() {
    }

    private ArrayList<NaughtyApp> tasks;

    public static FreezeTask getInstance() {
        if (singleInstance == null) {
            singleInstance = new FreezeTask();
        }
        return singleInstance;
    }

    public void init() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(NaughtyApp naughtyApp) {
        this.tasks.add(naughtyApp);
    }

    public void exec(Context context) {
        if (tasks.size() == 0) {
            Toast.makeText(context, "没有应用需要冻结", Toast.LENGTH_SHORT).show();
            return;
        }
        AsyncTask<String, Integer, Integer> task = new AsyncTask<String, Integer, Integer>() {

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
            }


            @Override
            protected Integer doInBackground(String... strings) {
                int i = 0;
                SettingsStateMachineManager.getInstance().resetDisable();
                while (i < tasks.size()) {
                    if (SettingsStateMachineManager.getInstance().isReset()) {
                        MyLogger.freeze.i("循环中一次");
                        // TODO：按名字判断是否是对应的settings
                        tasks.get(i).frozen(context);
                        publishProgress(i++);
                    }
                }
                return i;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Toast.makeText(context, "正在冻结应用", Toast.LENGTH_SHORT).show();
                MyLogger.freeze.i("冻结任务队列开始");

            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                MyLogger.freeze.i("冻结任务队列结束");
                Toast.makeText(context, "一共" + integer + "个应用冻结完成!", Toast.LENGTH_SHORT).show();
            }
        };
        task.execute();
    }
}
