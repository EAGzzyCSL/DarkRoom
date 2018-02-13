package me.eagzzycsl.darkroom.task

import android.content.Context
import android.os.AsyncTask
import me.eagzzycsl.darkroom.manager.DarkManager

class DarkTask : AsyncTask<Context, Int, Context>() {
    override fun doInBackground(vararg params: Context): Context {
        val context = params[0]
        while (!ActionQueue.allResolve) {
            ActionQueue.running(context)
        }
        return context
    }

    override fun onPostExecute(result: Context) {
        super.onPostExecute(result)
        DarkManager.onTaskDone(result)
    }
}