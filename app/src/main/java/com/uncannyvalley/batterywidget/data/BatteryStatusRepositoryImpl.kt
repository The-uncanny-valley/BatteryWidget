package com.uncannyvalley.batterywidget.data

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import com.uncannyvalley.batterywidget.domain.BatteryStatus

class BatteryStatusRepositoryImpl {
    fun getBatteryStatus(context: Context): BatteryStatus {

        // Get the system broadcast
        val intent = context.registerReceiver(
            null,
            IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        )

        val level = intent?.getIntExtra(
            BatteryManager.EXTRA_LEVEL,
            -1
        ) ?: -1
        // max level (usually 100)
        val scale = intent?.getIntExtra(
            BatteryManager.EXTRA_SCALE,
            -1
        ) ?: -1
        val percent = if (level >= 0 && scale > 0) (level * 100 / scale) else -1

        // Check charging status
        val status = intent?.getIntExtra(
            BatteryManager.EXTRA_STATUS, -1
        ) ?: -1
        val isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL

        return BatteryStatus(percent, isCharging)
    }
}