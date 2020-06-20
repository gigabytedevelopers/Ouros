package candybar.lib.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.danimahardhika.android.helpers.animation.AnimationHelper;
import com.danimahardhika.android.helpers.core.ColorHelper;
import com.danimahardhika.android.helpers.core.DrawableHelper;
import com.danimahardhika.android.helpers.core.ListHelper;
import com.danimahardhika.android.helpers.core.ViewHelper;
import com.danimahardhika.android.helpers.core.utils.LogUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pluscubed.recyclerfastscroll.RecyclerFastScroller;
import com.rafakob.drawme.DrawMeButton;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import candybar.lib.R;
import candybar.lib.adapters.WallpapersAdapter;
import candybar.lib.applications.CandyBarApplication;
import candybar.lib.databases.Database;
import candybar.lib.helpers.JsonHelper;
import candybar.lib.helpers.TapIntroHelper;
import candybar.lib.items.Wallpaper;
import candybar.lib.preferences.Preferences;
import candybar.lib.utils.listeners.WallpapersListener;

import static candybar.lib.helpers.ViewHelper.setFastScrollColor;

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

public class WallpapersFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipe;
    private ProgressBar mProgress;
    private RecyclerFastScroller mFastScroll;
    private DrawMeButton mPopupBubble;

    private AsyncTask mAsyncTask;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallpapers, container, false);
        mRecyclerView = view.findViewById(R.id.wallpapers_grid);
        mSwipe = view.findViewById(R.id.swipe);
        mProgress = view.findViewById(R.id.progress);
        mFastScroll = view.findViewById(R.id.fastscroll);
        mPopupBubble = view.findViewById(R.id.popup_bubble);

        if (!Preferences.get(getActivity()).isToolbarShadowEnabled()) {
            View shadow = view.findViewById(R.id.shadow);
            if (shadow != null) shadow.setVisibility(View.GONE);
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ViewCompat.setNestedScrollingEnabled(mRecyclerView, false);

        initPopupBubble();
        mProgress.getIndeterminateDrawable().setColorFilter(
                ColorHelper.getAttributeColor(getActivity(), R.attr.colorAccent),
                PorterDuff.Mode.SRC_IN);
        mSwipe.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.swipeRefresh));

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),
                getActivity().getResources().getInteger(R.integer.wallpapers_column_count)));

        if (CandyBarApplication.getConfiguration().getWallpapersGrid() == CandyBarApplication.GridStyle.FLAT) {
            int padding = getActivity().getResources().getDimensionPixelSize(R.dimen.card_margin);
            mRecyclerView.setPadding(padding, padding, 0, 0);
        }

        setFastScrollColor(mFastScroll);
        mFastScroll.attachRecyclerView(mRecyclerView);

        mSwipe.setOnRefreshListener(() -> {
            if (mProgress.getVisibility() == View.GONE)
                mAsyncTask = new WallpapersLoader(true).execute();
            else mSwipe.setRefreshing(false);
        });

        mAsyncTask = new WallpapersLoader(false).execute();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ViewHelper.resetSpanCount(mRecyclerView,
                getActivity().getResources().getInteger(R.integer.wallpapers_column_count));
    }

    @Override
    public void onDestroy() {
        if (mAsyncTask != null) mAsyncTask.cancel(true);
        ImageLoader.getInstance().getMemoryCache().clear();
        super.onDestroy();
    }

    private void initPopupBubble() {
        int color = ColorHelper.getAttributeColor(getActivity(), R.attr.colorAccent);
        mPopupBubble.setCompoundDrawablesWithIntrinsicBounds(DrawableHelper.getTintedDrawable(
                getActivity(), R.drawable.ic_toolbar_arrow_up, ColorHelper.getTitleTextColor(color)), null, null, null);
        mPopupBubble.setOnClickListener(view -> {
            WallpapersListener listener = (WallpapersListener) getActivity();
            listener.onWallpapersChecked(null);

            AnimationHelper.hide(getActivity().findViewById(R.id.popup_bubble))
                    .start();

            mAsyncTask = new WallpapersLoader(true).execute();
        });
    }

    private void showPopupBubble() {
        int wallpapersCount = Database.get(getActivity()).getWallpapersCount();
        if (wallpapersCount == 0) return;

        if (Preferences.get(getActivity()).getAvailableWallpapersCount() > wallpapersCount) {
            AnimationHelper.show(mPopupBubble)
                    .interpolator(new LinearOutSlowInInterpolator())
                    .start();
        }
    }

    private class WallpapersLoader extends AsyncTask<Void, Void, Boolean> {

        private List<Wallpaper> wallpapers;
        private boolean refreshing;

        private WallpapersLoader(boolean refreshing) {
            this.refreshing = refreshing;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (!refreshing) mProgress.setVisibility(View.VISIBLE);
            else mSwipe.setRefreshing(true);

            DrawMeButton popupBubble = getActivity().findViewById(R.id.popup_bubble);
            if (popupBubble.getVisibility() == View.VISIBLE) {
                AnimationHelper.hide(popupBubble).start();
            }
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            while (!isCancelled()) {
                try {
                    Thread.sleep(1);
                    if (!refreshing && (Database.get(getActivity()).getWallpapersCount() > 0)) {
                        wallpapers = Database.get(getActivity()).getWallpapers();
                        return true;
                    }

                    URL url = new URL(getString(R.string.wallpaper_json));
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(15000);

                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        InputStream stream = connection.getInputStream();
                        List list = JsonHelper.parseList(stream);
                        if (list == null) {
                            LogUtil.e("Json error, no array with name: "
                                    + CandyBarApplication.getConfiguration().getWallpaperJsonStructure().getArrayName());
                            return false;
                        }

                        if (refreshing) {
                            wallpapers = Database.get(getActivity()).getWallpapers();
                            List<Wallpaper> newWallpapers = new ArrayList<>();
                            for (int i = 0; i < list.size(); i++) {
                                Wallpaper wallpaper = JsonHelper.getWallpaper(list.get(i));
                                if (wallpaper != null) {
                                    newWallpapers.add(wallpaper);
                                }
                            }

                            List<Wallpaper> intersection = (List<Wallpaper>)
                                    ListHelper.intersect(newWallpapers, wallpapers);
                            List<Wallpaper> deleted = (List<Wallpaper>)
                                    ListHelper.difference(intersection, wallpapers);
                            List<Wallpaper> newlyAdded = (List<Wallpaper>)
                                    ListHelper.difference(intersection, newWallpapers);

                            Database.get(getActivity()).deleteWallpapers(deleted);
                            Database.get(getActivity()).addWallpapers(newlyAdded);

                            Preferences.get(getActivity()).setAvailableWallpapersCount(
                                    Database.get(getActivity()).getWallpapersCount());
                        } else {
                            if (Database.get(getActivity()).getWallpapersCount() > 0) {
                                Database.get(getActivity()).deleteWallpapers();
                            }

                            Database.get(getActivity()).addWallpapers(list);
                        }

                        wallpapers = Database.get(getActivity()).getWallpapers();
                        return true;
                    }
                } catch (Exception e) {
                    LogUtil.e(Log.getStackTraceString(e));
                    return false;
                }
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (getActivity() == null) return;
            if (getActivity().isFinishing()) return;

            mAsyncTask = null;
            mProgress.setVisibility(View.GONE);
            mSwipe.setRefreshing(false);

            if (aBoolean) {
                mRecyclerView.setAdapter(new WallpapersAdapter(getActivity(), wallpapers));

                WallpapersListener listener = (WallpapersListener) getActivity();
                listener.onWallpapersChecked(new Intent()
                        .putExtra("size", Preferences.get(getActivity()).getAvailableWallpapersCount())
                        .putExtra("packageName", getActivity().getPackageName()));

                try {
                    if (getActivity().getResources().getBoolean(R.bool.show_intro)) {
                        TapIntroHelper.showWallpapersIntro(getActivity(), mRecyclerView);
                    }
                } catch (Exception e) {
                    LogUtil.e(Log.getStackTraceString(e));
                }
            } else {
                Toast.makeText(getActivity(), R.string.connection_failed,
                        Toast.LENGTH_LONG).show();
            }
            showPopupBubble();
        }
    }
}
