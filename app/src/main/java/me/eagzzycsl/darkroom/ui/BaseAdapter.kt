package me.eagzzycsl.darkroom.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View

import java.util.ArrayList

import me.eagzzycsl.darkroom.model.MyApp

abstract class BaseAdapter<M : MyApp>(var context: Context)
    : RecyclerView.Adapter<BaseAdapter<M>.RecViewHolder<M>>() {
    private var dataArray: ArrayList<M> = ArrayList()

    abstract val layoutId: Int

    override fun onBindViewHolder(holder: RecViewHolder<M>, position: Int) {
        holder.setContent(dataArray[position])
    }

    override fun getItemCount(): Int {
        return dataArray.size
    }

    fun setDataArray(dataArray: ArrayList<M>) {
        this.dataArray = dataArray
    }

    abstract inner class RecViewHolder<in E : MyApp>(itemView: View) : RecyclerView.ViewHolder(itemView) {

        abstract fun setContent(data: E)
    }

}