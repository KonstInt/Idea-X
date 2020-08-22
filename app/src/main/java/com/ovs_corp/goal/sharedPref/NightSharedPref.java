package com.ovs_corp.goal.sharedPref;

import android.content.Context;
import android.content.SharedPreferences;

import com.ovs_corp.goal.AppInfo;

public class NightSharedPref {
    SharedPreferences theme;
    public NightSharedPref(Context context)
    {
        theme = context.getSharedPreferences("NightModeTheme",Context.MODE_PRIVATE);
    }

    public void setNightModeState(Boolean state) {
        SharedPreferences.Editor editor = theme.edit();
        editor.putBoolean("NightMode", state);
        editor.commit();
    }

    public Boolean loadNightModeState()
    {
      Boolean state = theme.getBoolean("NightMode", AppInfo.nightModeState);
      return state;
    }
}
