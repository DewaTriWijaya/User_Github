package com.dewatwc.githubuser.alarm

import `in`.abhisheksaxena.toaster.Toaster
import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.dewatwc.githubuser.R
import com.dewatwc.githubuser.ui.home.HomeActivity
import java.util.*

class Reminder: BroadcastReceiver() {
    companion object{
        private const val ALARM_ID = 101
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        notification(context)
    }

    private fun notification(context: Context?){
        val channelId = "reminder"
        val channelName = "reminder_gitHub"
        val intent = Intent(context, HomeActivity::class.java)
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val pendingIntent = TaskStackBuilder.create(context)
            .addParentStack(HomeActivity::class.java)
            .addNextIntent(intent)
            .getPendingIntent(ALARM_ID, PendingIntent.FLAG_UPDATE_CURRENT)
        val notificationManagerCompat = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationCompat.Builder(context, channelId)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentTitle("Reminder_GitHubUser")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentText(context.resources.getString(R.string.text_alrm))
            .setSound(alarmSound)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setAutoCancel(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            channel.apply {
                enableVibration(true)
                vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            }
            builder.setChannelId(channelId)
            notificationManagerCompat.createNotificationChannel(channel)
        }
        notificationManagerCompat.notify(ALARM_ID, builder.build())
    }

    fun setOnAlarm(context: Context?){
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent: PendingIntent = Intent(context, Reminder::class.java).let { intent ->
            PendingIntent.getBroadcast(context, ALARM_ID, intent, 0)
        }
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 9)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, intent)
        Toaster.popSuccess(context, "Alarm ON", Toaster.LENGTH_SHORT).show()
    }

    fun cancelAlarm(context: Context?){
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, Reminder::class.java)

        PendingIntent.getBroadcast(context, ALARM_ID, intent, 0).cancel()
        alarmManager.cancel(PendingIntent.getBroadcast(context, ALARM_ID, intent, 0))
        Toaster.popWarning(context, "Alarm OFF", Toaster.LENGTH_SHORT).show()
    }
}