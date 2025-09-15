package com.uncannyvalley.batterywidget.domain

import com.uncannyvalley.batterywidget.R

object BatteryIconMapper {
    fun getIconRes(status: BatteryStatus): Int {
        return if (status.isCharging) {
            when {
                status.percent >= 90 -> R.drawable.ic_battery_charging_100
                status.percent >= 70 -> R.drawable.ic_battery_charging_80
                status.percent >= 50 -> R.drawable.ic_battery_charging_60
                status.percent >= 30 -> R.drawable.ic_battery_charging_40
                status.percent >= 15 -> R.drawable.ic_battery_charging_20
                else -> R.drawable.ic_battery_charging_0
            }
        } else {
            when {
                status.percent >= 90 -> R.drawable.ic_battery_100
                status.percent >= 70 -> R.drawable.ic_battery_80
                status.percent >= 50 -> R.drawable.ic_battery_60
                status.percent >= 30 -> R.drawable.ic_battery_40
                status.percent >= 15 -> R.drawable.ic_battery_20
                else -> R.drawable.ic_battery_0
            }
        }
    }
}