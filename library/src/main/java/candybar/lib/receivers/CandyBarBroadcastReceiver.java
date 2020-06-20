package candybar.lib.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.danimahardhika.android.helpers.core.utils.LogUtil;

import candybar.lib.utils.listeners.WallpapersListener;

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

public class CandyBarBroadcastReceiver extends BroadcastReceiver {

    public static final String PROCESS_RESPONSE = "candybar.broadcast.receiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            if (intent == null) return;

            WallpapersListener listener = (WallpapersListener) context;
            listener.onWallpapersChecked(intent);
        } catch (Exception e) {
            LogUtil.e(Log.getStackTraceString(e));
        }
    }
}
