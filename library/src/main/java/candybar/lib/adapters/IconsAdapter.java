package candybar.lib.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.danimahardhika.android.helpers.core.SoftKeyboardHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import candybar.lib.R;
import candybar.lib.helpers.IconsHelper;
import candybar.lib.helpers.IntentHelper;
import candybar.lib.items.Icon;
import candybar.lib.preferences.Preferences;
import candybar.lib.utils.ImageConfig;

/*
 * CandyBar - Material Dashboard
 *
 * Copyright (c) 2014-2016 Dani Mahardhika
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class IconsAdapter extends RecyclerView.Adapter<IconsAdapter.ViewHolder> {

    private final Context mContext;
    private final List<Icon> mIcons;
    private List<Icon> mIconsAll;
    private final DisplayImageOptions.Builder mOptions;
    private final Fragment mFragment;

    private final boolean mIsShowIconName;

    public IconsAdapter(@NonNull Context context, @NonNull List<Icon> icons, boolean search, Fragment fragment) {
        mContext = context;
        mFragment = fragment;
        mIcons = icons;
        mIsShowIconName = mContext.getResources().getBoolean(R.bool.show_icon_name);
        if (search) {
            mIconsAll = new ArrayList<>();
            mIconsAll.addAll(mIcons);
        }

        mOptions = ImageConfig.getRawDefaultImageOptions();
        mOptions.resetViewBeforeLoading(true);
        mOptions.cacheInMemory(true);
        mOptions.cacheOnDisk(false);
        mOptions.displayer(new FadeInBitmapDisplayer(700));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.fragment_icons_item_grid, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(mIcons.get(position).getTitle());

        ImageLoader.setShape(Preferences.get(mContext).getIconShape());

        ImageLoader.getInstance().displayImage("drawable://" + mIcons.get(position).getRes(),
                new ImageViewAware(holder.icon), mOptions.build(),
                new ImageSize(272, 272), null, null);
    }

    @Override
    public int getItemCount() {
        return mIcons.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView icon;
        private final TextView name;
        private final LinearLayout container;

        ViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            name = itemView.findViewById(R.id.name);
            container = itemView.findViewById(R.id.container);
            container.setOnClickListener(this);

            if (!mIsShowIconName) {
                name.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            int position = getAdapterPosition();
            if (id == R.id.container) {
                if (position < 0 || position > mIcons.size()) return;
                SoftKeyboardHelper.closeKeyboard(mContext);
                IconsHelper.selectIcon(mContext, IntentHelper.sAction, mIcons.get(position));
            }
        }
    }

    public void search(String string) {
        String query = string.toLowerCase(Locale.getDefault()).trim();
        mIcons.clear();
        if (query.length() == 0) mIcons.addAll(mIconsAll);
        else {
            for (int i = 0; i < mIconsAll.size(); i++) {
                Icon icon = mIconsAll.get(i);
                String title = icon.getTitle().toLowerCase(Locale.getDefault());
                if (title.contains(query)) {
                    mIcons.add(icon);
                }
            }
        }
        notifyDataSetChanged();
    }
}
