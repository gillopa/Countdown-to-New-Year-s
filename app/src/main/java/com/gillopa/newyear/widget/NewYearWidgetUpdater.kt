package com.gillopa.newyear.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.gillopa.newyear.CountdownCalculator
import com.gillopa.newyear.MainActivity
import com.gillopa.newyear.R

object NewYearWidgetUpdater {

    fun updateAll(context: Context) {
        val manager = AppWidgetManager.getInstance(context)
        val ids = manager.getAppWidgetIds(
            ComponentName(context, NewYearWidgetProvider::class.java)
        )
        if (ids.isNotEmpty()) {
            updateWidgets(context, manager, ids)
        }
    }

    fun updateWidgets(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        val days = CountdownCalculator.daysUntilNewYear()
        val year = CountdownCalculator.targetYear()
        val isToday = CountdownCalculator.isNewYearsDay()

        for (id in appWidgetIds) {
            val views = RemoteViews(context.packageName, R.layout.widget_new_year)

            if (isToday) {
                views.setTextViewText(R.id.widget_title, "С Новым годом!")
                views.setTextViewText(R.id.widget_days, "✦")
                views.setTextViewText(R.id.widget_label, "праздник уже здесь")
                views.setTextViewText(R.id.widget_vibe, "Пусть год будет тёплым")
            } else {
                views.setTextViewText(R.id.widget_title, "До Нового года")
                views.setTextViewText(R.id.widget_days, days.toString())
                views.setTextViewText(
                    R.id.widget_label,
                    "${CountdownCalculator.daysWord(days)} · $year"
                )
                views.setTextViewText(R.id.widget_vibe, CountdownCalculator.vibeLine(days))
            }

            val openApp = PendingIntent.getActivity(
                context,
                0,
                Intent(context, MainActivity::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            views.setOnClickPendingIntent(R.id.widget_root, openApp)

            appWidgetManager.updateAppWidget(id, views)
        }
    }
}
