package me.eagzzycsl.darkroom.manager

import android.app.Activity
import android.content.Context
import android.widget.Toast
import me.eagzzycsl.darkroom.R
import me.eagzzycsl.darkroom.action.FreezeAction
import me.eagzzycsl.darkroom.action.ReleaseAction
import me.eagzzycsl.darkroom.model.NaughtyApp
import me.eagzzycsl.darkroom.task.ActionQueue
import me.eagzzycsl.darkroom.task.DarkTask

object DarkManager {
    enum class WorkMode {
        ReleaseAndLaunchOne,
        ReleaseAll,
        FreezeOne,
        FreezeAll,
    }

    val inWorking
        get() = working;
    private var working = false;
    private var workMode = WorkMode.FreezeAll;
    private fun init(workMode: WorkMode) {
        this.workMode = workMode
        ActionQueue.reset();
        this.working = true;
    }

    fun release(naughtyApp: NaughtyApp, context: Context) {
        this.init(WorkMode.ReleaseAndLaunchOne)
        ActionQueue.append(ReleaseAction(naughtyApp))
        DarkTask().execute(context)
    }

    fun freeze(naughtyApp: NaughtyApp, context: Context) {
        this.init(WorkMode.FreezeOne)
        ActionQueue.append(FreezeAction(naughtyApp))
        DarkTask().execute(context)
    }

    fun freezeAll(naughtyApps: List<NaughtyApp>, context: Context) {

        this.init(WorkMode.FreezeAll)
        ActionQueue.appendAll(naughtyApps.map { FreezeAction(it) })
        DarkTask().execute(context)
    }

    fun releaseAll(naughtyApps: Array<out NaughtyApp>, context: Context) {
        this.init(WorkMode.ReleaseAll)
        ActionQueue.appendAll(naughtyApps.map { ReleaseAction(it) })
        DarkTask().execute(context)
    }

    fun startWorking() {
        this.working = true
    }

    fun onTaskDone(context: Context) {
        this.working = false
        AppManager.letMeTop(context)
        when (workMode) {
            WorkMode.ReleaseAndLaunchOne -> {
                val theAction = ActionQueue.actions[0]
                AppManager.launchApp(context, theAction.app)
                if (SettingsManager.autoFinishAfterLaunch) {
                    (context as Activity).finish()
                }
            }
            WorkMode.ReleaseAll -> {

            }
            WorkMode.FreezeOne -> {

            }
            WorkMode.FreezeAll -> {
                Toast.makeText(
                        context,
                        "${ActionQueue.actions.size}${context.getString(R.string.freezeAppDone)}",
                        Toast.LENGTH_SHORT
                ).show()
                if (SettingsManager.autoFinishAfterFreeze) {
                    (context as Activity).finish()
                }
            }
        }
    }
}