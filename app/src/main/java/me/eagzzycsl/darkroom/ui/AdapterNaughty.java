package me.eagzzycsl.darkroom.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import me.eagzzycsl.darkroom.model.NaughtyApp;
import me.eagzzycsl.darkroom.R;
import me.eagzzycsl.darkroom.manager.AppManager;
import me.eagzzycsl.darkroom.manager.ShortCutManager;

class AdapterNaughty extends BaseAdapter<NaughtyApp> {
    AdapterNaughty(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_item_naughty;
    }

    @Override
    public NaughtyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NaughtyHolder(LayoutInflater.from(getContext()).inflate(getLayoutId(), parent, false));
    }

    private class NaughtyHolder extends RecViewHolder<NaughtyApp> {
        private final TextView item_name;
        private final ImageView item_icon;
        private final View item_add_shortcut;

        NaughtyHolder(View itemView) {
            super(itemView);
            item_name = itemView.findViewById(R.id.item_name);
            item_icon = itemView.findViewById(R.id.item_icon);
            item_add_shortcut = itemView.findViewById(R.id.item_add_shortcut);
            itemView.setOnClickListener(view -> {
                NaughtyApp naughtyApp = (NaughtyApp) itemView.getTag();
                naughtyApp.launch(getContext());
            });
            itemView.setOnLongClickListener(view -> {
                NaughtyApp naughtyApp = (NaughtyApp) itemView.getTag();
                AppManager.INSTANCE.gotoSettings(getContext(), naughtyApp);
                return true;
            });
            item_add_shortcut.setOnClickListener(view -> {
                NaughtyApp naughtyApp = (NaughtyApp) itemView.getTag();
                ShortCutManager.INSTANCE.createShortcut(getContext(),naughtyApp);
            });
        }

        @Override
        public void setContent(NaughtyApp naughtyApp) {
            itemView.setTag(naughtyApp);
            item_name.setText(naughtyApp.getAppName());
            item_icon.setImageDrawable(naughtyApp.getAppIcon());
            if (naughtyApp.isEnable(getContext())) {
                item_name.setTextColor(getContext().getColor(android.R.color.black));
            } else {
                item_name.setTextColor(getContext().getColor(R.color.colorAccent));
            }
        }
    }
}
