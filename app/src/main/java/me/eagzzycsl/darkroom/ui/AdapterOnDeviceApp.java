package me.eagzzycsl.darkroom.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import me.eagzzycsl.darkroom.R;
import me.eagzzycsl.darkroom.model.OnDeviceApp;

class AdapterOnDeviceApp extends BaseAdapter<OnDeviceApp> {
    AdapterOnDeviceApp(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_item_on_device;
    }

    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserHolder(LayoutInflater.from(getContext()).inflate(getLayoutId(), parent, false));
    }

    private class UserHolder extends RecViewHolder<OnDeviceApp> implements View.OnClickListener {
        private final TextView item_name;
        private final ImageView item_icon;


        UserHolder(View itemView) {
            super(itemView);
            item_name = itemView.findViewById(R.id.item_name);
            item_icon = itemView.findViewById(R.id.item_icon);
            itemView.setOnClickListener(this);

        }

        @Override
        public void setContent(OnDeviceApp onDeviceApp) {
            item_name.setText(onDeviceApp.getAppName());
            item_icon.setImageDrawable(onDeviceApp.getAppIcon());
            if (onDeviceApp.getFrozen()) {
                item_name.setTextColor(getContext().getColor(R.color.colorPrimary));
            } else {
                item_name.setTextColor(getContext().getColor(android.R.color.black));
            }
            itemView.setTag(onDeviceApp);
        }

        @Override
        public void onClick(View view) {
            OnDeviceApp onDeviceApp = (OnDeviceApp) view.getTag();
            onDeviceApp.toggleFrozen();
            AdapterOnDeviceApp.this.notifyItemChanged(this.getLayoutPosition());
        }
    }

}
