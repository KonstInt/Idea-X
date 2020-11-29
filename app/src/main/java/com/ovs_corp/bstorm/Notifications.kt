package com.ovs_corp.bstorm

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class Notifications : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        if (AppInfo.nightModeState) setTheme(R.style.NightAppTheme) else setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)

    }

    fun back_from_ic(view: View) {
        super.onBackPressed();
    }
}