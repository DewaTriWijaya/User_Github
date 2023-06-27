package com.dewatwc.githubuser.widget

import android.content.Intent
import android.widget.RemoteViewsService

class WidgetService : RemoteViewsService() {

    override fun onGetViewFactory(p0: Intent?): RemoteViewsFactory {
        return WidgetRemote(this.applicationContext)
    }
}