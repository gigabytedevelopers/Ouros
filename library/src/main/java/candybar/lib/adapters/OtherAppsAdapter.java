package candybar.lib.adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.danimahardhika.android.helpers.core.DrawableHelper;
import com.danimahardhika.android.helpers.core.utils.LogUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import candybar.lib.R;
import candybar.lib.applications.CandyBarApplication;
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

public class OtherAppsAdapter extends BaseAdapter {

    private Context mContext;
    private List<CandyBarApplication.OtherApp> mOtherApps;

    public OtherAppsAdapter(@NonNull Context context, @NonNull List<CandyBarApplication.OtherApp> otherApps) {
        mContext = context;
        mOtherApps = otherApps;
    }

    @Override
    public int getCount() {
        return mOtherApps.size();
    }

    @Override
    public CandyBarApplication.OtherApp getItem(int position) {
        return mOtherApps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = View.inflate(mContext, R.layout.fragment_other_apps_item_list, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        CandyBarApplication.OtherApp otherApp = mOtherApps.get(position);
        String uri = otherApp.getIcon();
        if (!URLUtil.isValidUrl(uri)) {
            uri = "drawable://" + DrawableHelper.getResourceId(mContext, uri);
        }

        ImageLoader.getInstance().displayImage(
                uri, holder.image, ImageConfig.getDefaultImageOptions(true));
        holder.title.setText(otherApp.getTitle());

        if (otherApp.getDescription() == null || otherApp.getDescription().length() == 0) {
            holder.desc.setVisibility(View.GONE);
        } else {
            holder.desc.setText(otherApp.getDescription());
            holder.desc.setVisibility(View.VISIBLE);
        }

        holder.container.setOnClickListener(v -> {
            if (!URLUtil.isValidUrl(otherApp.getUrl())) return;

            try {
                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(otherApp.getUrl())));
            } catch (ActivityNotFoundException e) {
                LogUtil.e(Log.getStackTraceString(e));
            }
        });
        return view;
    }

    private class ViewHolder {

        private LinearLayout container;
        private ImageView image;
        private TextView title;
        private TextView desc;

        ViewHolder(View view) {
            container = view.findViewById(R.id.container);
            image = view.findViewById(R.id.image);
            title = view.findViewById(R.id.title);
            desc = view.findViewById(R.id.desc);
        }
    }
}
