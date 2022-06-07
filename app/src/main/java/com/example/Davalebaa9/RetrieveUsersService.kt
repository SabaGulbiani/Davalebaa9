package com.example.Davalebaa9

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder

import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat

import retrofit2.Response


class RetrieveUsersService : Service() {

    companion object {
        private var onStart: () -> Unit = { }
        private var onComplete: (List<User>) -> Unit = { }
        private var onFailure: (Throwable) -> Unit = { }

        fun onStart(onStart: () -> Unit) {
            this.onStart = onStart
        }

        fun doOnComplete(onComplete: (List<User>) -> Unit) {
            this.onComplete = onComplete
        }

        fun doOnFailure(onFailure: (Throwable) -> Unit) {
            this.onFailure = onFailure
        }
    }

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        onStart.invoke()
        Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show()
        API.create().getUsers().enqueue(object : retrofit2.Callback<UserList> {
            override fun onResponse(call: retrofit2.Call<UserList>, response: Response<UserList>) {
                if (response.isSuccessful) {
                    onComplete.invoke(response.body()?.data.orEmpty())
                } else {
                    onFailure.invoke(Throwable(response.errorBody()?.string()))
                }
                stopForeground(true)
            }

            override fun onFailure(call: retrofit2.Call<UserList>, t: Throwable) {
                onFailure.invoke(t)
                stopForeground(true)
            }
        })

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        super.onCreate()
        showNotification()
    }

    private fun showNotification() {
        NotificationCompat.Builder(this, "BG_SERVICE").also {
            it.setContentTitle("Running...")
            it.setContentText("Service is running in the background.")
            it.setAutoCancel(true)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                createNotificationChannel()
        }.build().let {
            startForeground(1, it)
        }
    }

    @RequiresApi(26)
    private fun createNotificationChannel() {
        NotificationChannel(
            "BG_SERVICE",
            "Background Service",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            (getSystemService(NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(
                this
            )
        }
    }
}