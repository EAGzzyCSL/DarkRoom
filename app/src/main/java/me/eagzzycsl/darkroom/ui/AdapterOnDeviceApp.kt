package me.eagzzycsl.darkroom.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import me.eagzzycsl.darkroom.R
import me.eagzzycsl.darkroom.model.MetaApp

internal class AdapterOnDeviceApp(context: Context) : BaseAdapter<MetaApp>(context) {

    override val layoutId: Int
        get() = R.layout.layout_item_on_device

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnDeviceHolder {
        return OnDeviceHolder(LayoutInflater.from(context).inflate(layoutId, parent, false))
    }

    inner class OnDeviceHolder internal constructor(itemView: View) : BaseAdapter<MetaApp>.RecViewHolder<MetaApp>(itemView), View.OnClickListener {
        private val itemName: TextView = itemView.findViewById(R.id.item_name)
        private val itemIcon: ImageView = itemView.findViewById(R.id.item_icon)


        init {
            itemView.setOnClickListener(this)
        }

        override fun setContent(data: MetaApp) {
            itemName.text = data.appName
            itemIcon.setImageDrawable(data.appIcon)
            if (data.willFreeze) {
                itemName.setTextColor(context.getColor(R.color.colorPrimary))
            } else {
                itemName.setTextColor(context.getColor(android.R.color.black))
            }
            itemView.tag = data
        }

        override fun onClick(view: View) {
            val onDeviceApp = view.tag as MetaApp
            onDeviceApp.toggleWillFreeze()
            this@AdapterOnDeviceApp.notifyItemChanged(this.layoutPosition)
        }
    }

}
