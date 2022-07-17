package com.app.illremind

import android.app.*
import android.app.Notification
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.illremind.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createNotificationChannel()

        binding.submit.setOnClickListener{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                scheduleNotification()
            }
        }

    }
    private fun scheduleNotification() {
        val intent = Intent(applicationContext, Notification::class.java)
        val title = binding.titleET.text.toString()
        val message = binding.messageET.text.toString()
        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getTime()
        } else {
            TODO("VERSION.SDK_INT < M")
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                time,
                pendingIntent
            )
        }
        showAlert(time, title, message)
    }

    private fun showAlert(time: Long, title: String, message: String) {
        val date = Date(time)
        val dateFormat = android.text.format.DateFormat.getLongDateFormat(applicationContext)
        val timeFormat = android.text.format.DateFormat.getTimeFormat(applicationContext)

        AlertDialog.Builder(this)
            .setTitle("Notification Scheduled")
            .setMessage(
                "Title: "+ title +
                        "\nMessage: "+ message +
                        "\nAt: " + dateFormat.format(date) + " "+timeFormat.format(time))
            .setPositiveButton("Okay"){_,_ ->}
            .show()
    }

    private fun getTime(): Long {
        val minute = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.timePicker.minute
        } else {
            TODO("VERSION.SDK_INT < M")
        }
        val hour = binding.timePicker.hour
        val day = binding.datePicker.dayOfMonth
        val month = binding.datePicker.month
        val year = binding.datePicker.year

        val calender = Calendar.getInstance()
        calender.set(year, month, day, hour, minute)
        return  calender.timeInMillis
    }

    private fun createNotificationChannel() {
        val name = "Notif Channel"
        val decs = "A describtion of the channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(channelID, name, importance)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        channel.description = decs
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }


}