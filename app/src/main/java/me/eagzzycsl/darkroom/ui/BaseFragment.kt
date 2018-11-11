package me.eagzzycsl.darkroom.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import java.util.ArrayList

import me.eagzzycsl.darkroom.R

abstract class BaseFragment<MetaApp> : Fragment() {
    private var recyclerView: RecyclerView? = null
    private var adapter: BaseAdapter<MetaApp>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = getAdapter()
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        recyclerView?.itemAnimator = DefaultItemAnimator()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.layout_rec_fragment, container, false)
        recyclerView = view?.findViewById(R.id.fragment_recyclerView)
        return view
    }

    abstract fun getAdapter(): BaseAdapter<MetaApp>

    fun setData(dataArray: ArrayList<MetaApp>) {
        adapter?.setDataArray(dataArray)
        adapter?.notifyDataSetChanged()
    }

    fun updateData() {
        adapter?.notifyDataSetChanged()
    }

}