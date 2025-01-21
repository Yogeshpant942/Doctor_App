package com.example.doctorapp
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class NotificationWorker(context:Context,workerParameters: WorkerParameters) :Worker(context,workerParameters){
    override fun doWork(): Result {
        showNotification("Appointment Remainder","It time for appointment")
   return Result.success()
    }

    private fun showNotification(title: String, message: String) {
         val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId =  "appointment_remainder_channel"

        if(android.os.Build.VERSION.SDK_INT>= android.os.Build.VERSION_CODES.O){
            val channel = NotificationChannel(channelId,"Appointment Reminder Notifications",NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }
        val notification = NotificationCompat.Builder(applicationContext,channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(1,notification)
    }

}