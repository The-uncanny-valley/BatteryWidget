package com.uncannyvalley.batterywidget.domain

data class BatteryStatus(
    val percent: Int,
    val isCharging: Boolean
)