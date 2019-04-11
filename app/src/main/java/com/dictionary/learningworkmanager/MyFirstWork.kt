package com.dictionary.learningworkmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class MyFirstWork(
    context: Context,
    workerParameters: WorkerParameters)
    : Worker(context,workerParameters){
    companion object {
        val channelId = "abhishek_notification"
    }

    override fun doWork(): Result {
        runBlocking {
            delay(2000)

            val message = inputData.getString("message")
            showNotification(message!!)


        }
        val data = Data.Builder()
            .putString("message","The Task completed Successfully")
            .build()
        return Result.success(data)
    }

    private fun showNotification(s: String) {
        createNotificationChannel(applicationContext)
        SimpleNotification.notify(applicationContext,s, channelId)
    }

    private fun createNotificationChannel(context: Context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "My Notification Channel"
            val description = "My Notification Channel Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance)
            channel.description = description
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager!!.createNotificationChannel(channel)
        }
    }

}