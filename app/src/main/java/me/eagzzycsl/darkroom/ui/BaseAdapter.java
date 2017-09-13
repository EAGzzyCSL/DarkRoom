package me.eagzzycsl.darkroom.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import me.eagzzycsl.darkroom.model.MyApp;

abstract class BaseAdapter<M extends MyApp> extends RecyclerView.Adapter<BaseAdapter<M>.RecViewHolder<M>> {
    protected Context context;
    private ArrayList<M> dataArray;

    BaseAdapter(Context context) {
        this.context = context;
    }

    @Override
    public void onBindViewHolder(RecViewHolder<M> holder, int position) {
        holder.setContent(dataArray.get(position));
    }

    @Override
    public int getItemCount() {
        return dataArray == null ? 0 : dataArray.size();
    }

    public abstract int getLayoutId();

    void setDataArray(ArrayList<M> dataArray) {
        this.dataArray = dataArray;
    }

    abstract class RecViewHolder<E extends MyApp> extends RecyclerView.ViewHolder {
        RecViewHolder(View itemView) {
            super(itemView);
        }

        public abstract void setContent(E data);
    }

}