package com.dewatwc.githubuser.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.net.toUri
import com.dewatwc.githubuser.R
import com.dewatwc.githubuser.splashscreen.SplashScreen

class Widget : AppWidgetProvider() {

    companion object {

        fun updateAppWidget(
            context: Context,
            WidgetManager: AppWidgetManager,
            WidgetId: Int
        ) {
           
            val remoteView = RemoteViews(context.packageName, R.layout.widget).apply {
                setRemoteAdapter(R.id.stackView, Intent(context, WidgetService::class.java).apply {
                    putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, WidgetId)
                    data = toUri(Intent.URI_INTENT_SCHEME).toUri()
                })

                setOnClickPendingIntent(
                    R.id.widget_title,
                    PendingIntent.getActivity(
                        context,
                        0,
                        Intent(context, SplashScreen::class.java).apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK },
                        0
                    )
                )
            }
            WidgetManager.updateAppWidget(WidgetId, remoteView)
        }

        fun refreshBroadcast(context: Context) {
            val refresh = Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE).apply {
                component = ComponentName(context, Widget::class.java)
            }
            context.sendBroadcast(refresh)
        }
    }

    override fun onUpdate(
        context: Context,
        WidgetManager: AppWidgetManager,
        WidgetIds: IntArray
    ) {
        for (WidgetId in WidgetIds) {
            updateAppWidget(context, WidgetManager, WidgetId)
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if (intent?.action != null) {
            if (intent.action == AppWidgetManager.ACTION_APPWIDGET_UPDATE) {
                val component = context?.let {
                    ComponentName(context, Widget::class.java)
                }
                AppWidgetManager.getInstance(context).apply {
                    notifyAppWidgetViewDataChanged(
                        getAppWidgetIds(component),
                        R.id.stackView
                    )
                }
            }
        }
    }
}
