package me.eagzzycsl.darkroom.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import me.eagzzycsl.darkroom.R
import me.eagzzycsl.darkroom.manager.AppManager
import me.eagzzycsl.darkroom.manager.ShortCutManager
import me.eagzzycsl.darkroom.model.MetaApp

internal class AdapterNaughty(context: Context) : BaseAdapter<MetaApp>(context) {

    override val layoutId: Int
        get() = R.layout.layout_item_naughty

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NaughtyHolder {
        return NaughtyHolder(LayoutInflater.from(context).inflate(layoutId, parent, false))
    }

    internal inner class NaughtyHolder internal constructor(itemView: View) : BaseAdapter<MetaApp>.RecViewHolder<MetaApp>(itemView) {
        private val itemName: TextView = itemView.findViewById(R.id.item_name)
        private val itemIcon: ImageView = itemView.findViewById(R.id.item_icon)
        private val itemAddShortcut: View = itemView.findViewById(R.id.item_add_shortcut)
        private val itemRelease: View = itemView.findViewById(R.id.item_release)

        init {
            itemView.setOnClickListener {
                val naughtyApp = itemView.tag as MetaApp
                naughtyApp.launch(context)
            }
            itemView.setOnLongClickListener {
                val naughtyApp = itemView.tag as MetaApp
                AppManager.gotoSettings(context, naughtyApp)
                true
            }
            itemAddShortcut.setOnClickListener {
                val naughtyApp = itemView.tag as MetaApp
                ShortCutManager.createShortcut(context, naughtyApp)
            }
            itemRelease.setOnClickListener {
                val naughtyApp = itemView.tag as MetaApp

            }
        }

        override fun setContent(data: MetaApp) {
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
