package me.eagzzycsl.darkroom.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import me.eagzzycsl.darkroom.model.NaughtyApp
import me.eagzzycsl.darkroom.R
import me.eagzzycsl.darkroom.manager.AppManager
import me.eagzzycsl.darkroom.manager.ShortCutManager

internal class AdapterNaughty(context: Context) : BaseAdapter<NaughtyApp>(context) {

    override val layoutId: Int
        get() = R.layout.layout_item_naughty

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NaughtyHolder {
        return NaughtyHolder(LayoutInflater.from(context).inflate(layoutId, parent, false))
    }

    internal inner class NaughtyHolder internal constructor(itemView: View) : BaseAdapter<NaughtyApp>.RecViewHolder<NaughtyApp>(itemView) {
        private val itemName: TextView = itemView.findViewById(R.id.item_name)
        private val itemIcon: ImageView = itemView.findViewById(R.id.item_icon)
        private val itemAddShortcut: View = itemView.findViewById(R.id.item_add_shortcut)

        init {
            itemView.setOnClickListener {
                val naughtyApp = itemView.tag as NaughtyApp
                naughtyApp.launch(context)
            }
            itemView.setOnLongClickListener {
                val naughtyApp = itemView.tag as NaughtyApp
                AppManager.gotoSettings(context, naughtyApp)
                true
            }
            itemAddShortcut.setOnClickListener {
                val naughtyApp = itemView.tag as NaughtyApp
                ShortCutManager.createShortcut(context, naughtyApp)
            }
        }

        override fun setContent(data: NaughtyApp) {
            itemView.tag = data
            itemName.text = data.appName
            itemIcon.setImageDrawable(data.appIcon)
            if (data.isEnable(context)) {
                itemName.setTextColor(context.getColor(android.R.color.black))
            } else {
                itemName.setTextColor(context.getColor(R.color.colorAccent))
            }
        }
    }
}
