package me.eagzzycsl.darkroom.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import me.eagzzycsl.darkroom.model.MyApp;
import me.eagzzycsl.darkroom.R;

public abstract class BaseFragment<M extends MyApp> extends Fragment {
    private RecyclerView recyclerView;
    private BaseAdapter<M> adapter;
    private boolean containData = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = getAdapter();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_rec_fragment, container,false);
        recyclerView = view.findViewById(R.id.fragment_recyclerView);
        return view;
    }

    public abstract BaseAdapter<M> getAdapter();

    public void setData(ArrayList<M> dataArray) {
        if (adapter != null) {
            adapter.setDataArray(dataArray);
            containData = true;
            adapter.notifyDataSetChanged();
        }
    }

    public void updateData() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    public boolean isContainData() {
        return this.containData;
    }

}