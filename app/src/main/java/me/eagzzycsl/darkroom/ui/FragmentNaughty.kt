package me.eagzzycsl.darkroom.ui


import android.os.Bundle
import java.util.ArrayList

import me.eagzzycsl.darkroom.manager.AppList
import me.eagzzycsl.darkroom.model.NaughtyApp

class FragmentNaughty : BaseFragment<NaughtyApp>() {
    val data: ArrayList<NaughtyApp> = AppList.naughtyApps

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setData(data)
    }

    override fun onResume() {
        super.onResume()
        updateData()
    }

    override fun getAdapter(): BaseAdapter<NaughtyApp> {
        return AdapterNaughty(activity)
    }

}
