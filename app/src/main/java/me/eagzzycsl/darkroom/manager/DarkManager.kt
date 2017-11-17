package me.eagzzycsl.darkroom.manager

import android.content.Context
import android.widget.Toast
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
    var working = false;
    var workMode = WorkMode.FreezeAll;
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

    fun freezeAll(naughtyApps: Array<out NaughtyApp>, context: Context) {

        this.init(WorkMode.FreezeAll)
        ActionQueue.appendAll(naughtyApps.map { FreezeAction(it) }.toTypedArray<FreezeAction>())
        DarkTask().execute(context)
    }

    fun releaseAll(naughtyApps: Array<out NaughtyApp>, context: Context) {
        this.init(WorkMode.ReleaseAll)
        ActionQueue.appendAll(naughtyApps.map { ReleaseAction(it) }.toTypedArray<ReleaseAction>())
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
                // TODO：从actions里面拿似乎不太方便
                val theAction = ActionQueue.actions[0]
                AppManager.launchApp(context, theAction.app)
            }
            WorkMode.ReleaseAll -> {

            }
            WorkMode.FreezeOne -> {

            }
            WorkMode.FreezeAll -> {

                Toast.makeText(context, "冻结完成", Toast.LENGTH_SHORT).show()
            }
        }
    }
}