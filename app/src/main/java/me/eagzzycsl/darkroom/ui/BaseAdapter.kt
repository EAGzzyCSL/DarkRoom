package me.eagzzycsl.darkroom.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View

import java.util.ArrayList

abstract class BaseAdapter<MetaApp>(var context: Context)
    : RecyclerView.Adapter<BaseAdapter<MetaApp>.RecViewHolder<MetaApp>>() {
    private var dataArray: ArrayList<MetaApp> = ArrayList()

    abstract val layoutId: Int

    override fun onBindViewHolder(holder: RecViewHolder<MetaApp>, position: Int) {
        holder.setContent(dataArray[position])
    }

    override fun getItemCount(): Int {
        return dataArray.size
    }

    fun setDataArray(dataArray: ArrayList<MetaApp>) {
        this.dataArray = dataArray
    }

    abstract inner class RecViewHolder<in MetaApp>(itemView: View) : RecyclerView.ViewHolder(itemView) {

        abstract fun setContent(data: MetaApp)
    }

}