package candybar.lib.fragments.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.afollestad.materialdialogs.MaterialDialog;

import candybar.lib.R;
import candybar.lib.adapters.ChangelogAdapter;
import candybar.lib.helpers.TypefaceHelper;
import candybar.lib.utils.listeners.HomeListener;

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

public class ChangelogFragment extends DialogFragment {

    private ListView mChangelogList;
    private TextView mChangelogDate;
    private TextView mChangelogVersion;

    private static final String TAG = "candybar.dialog.changelog";

    private static ChangelogFragment newInstance() {
        return new ChangelogFragment();
    }

    public static void showChangelog(FragmentManager fm) {
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag(TAG);
        if (prev != null) {
            ft.remove(prev);
        }

        try {
            DialogFragment dialog = ChangelogFragment.newInstance();
            dialog.show(ft, TAG);
        } catch (IllegalArgumentException | IllegalStateException ignored) {
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity());
        builder.typeface(
                TypefaceHelper.getMedium(getActivity()),
                TypefaceHelper.getRegular(getActivity()));
        builder.customView(R.layout.fragment_changelog, false);
        builder.positiveText(R.string.close);
        MaterialDialog dialog = builder.build();
        dialog.show();

        mChangelogList = (ListView) dialog.findViewById(R.id.changelog_list);
        mChangelogDate = (TextView) dialog.findViewById(R.id.changelog_date);
        mChangelogVersion = (TextView) dialog.findViewById(R.id.changelog_version);
        return dialog;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            String version = getActivity().getPackageManager().getPackageInfo(
                    getActivity().getPackageName(), 0).versionName;
            if (version != null && version.length() > 0) {
                mChangelogVersion.setText(getActivity().getResources().getString(
                        R.string.changelog_version) + " " + version);
            }
        } catch (Exception ignored) {
        }

        String date = getActivity().getResources().getString(R.string.changelog_date);
        if (date.length() > 0) mChangelogDate.setText(date);
        else mChangelogDate.setVisibility(View.GONE);

        String[] changelog = getActivity().getResources().getStringArray(R.array.changelog);
        mChangelogList.setAdapter(new ChangelogAdapter(getActivity(), changelog));
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

        FragmentManager fm = getActivity().getSupportFragmentManager();
        if (fm != null) {
            Fragment fragment = fm.findFragmentByTag("home");
            if (fragment != null) {
                HomeListener listener = (HomeListener) fragment;
                listener.onHomeIntroInit();
            }
        }
    }
}
