package com.uncannyvalley.batterywidget.presentation

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.uncannyvalley.batterywidget.R
import com.uncannyvalley.batterywidget.data.BatteryStatusRepositoryImpl
import com.uncannyvalley.batterywidget.domain.BatteryIconMapper

class BatteryWidget: AppWidgetProvider() {
    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        if (context != null && appWidgetManager != null && appWidgetIds != null) {
            val repository = BatteryStatusRepositoryImpl()
            for (id in appWidgetIds) {
                val status = repository.getBatteryStatus(context)
                val iconRes = BatteryIconMapper.getIconRes(status)

                val views = RemoteViews(context.packageName, R.layout.widget_battery).apply {
                    setTextViewText(R.id.batteryText,
                        if (status.percent >= 0) "${status.percent}%" else "--%"
                    )
                    setImageViewResource(R.id.batteryIcon, iconRes)

                    val intent = Intent(context, BatteryWidget::class.java).apply {
                        action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
                        putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, intArrayOf(id))
                    }
                    val pendingIntent = PendingIntent.getBroadcast(
                        context, id, intent, PendingIntent.FLAG_IMMUTABLE
                    )
                    setOnClickPendingIntent(R.id.widgetLayout, pendingIntent)
                }

                appWidgetManager.updateAppWidget(id, views)
            }
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if (context != null && intent?.action == Intent.ACTION_BATTERY_CHANGED) {
            val manager = AppWidgetManager.getInstance(context)
            val ids = manager.getAppWidgetIds(ComponentName(context, BatteryWidget::class.java))
            val repository = BatteryStatusRepositoryImpl()
            for (id in ids) {
                val status = repository.getBatteryStatus(context)

                val iconRes = BatteryIconMapper.getIconRes(status)
                val views = RemoteViews(context.packageName, R.layout.widget_battery)
                views.setTextViewText(
                    R.id.batteryText, if (status.percent >= 0) "${status.percent}%" else "--%"
                )
                views.setImageViewResource(R.id.batteryIcon, iconRes)
                manager.updateAppWidget(id, views)
            }
        }
    }
}