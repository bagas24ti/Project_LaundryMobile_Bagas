package com.example.project_laundry_bagas.home

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.project_laundry_bagas.BaseActivity

/**
 * Class Helper untuk mengelola pembuatan NotificationChannel dan pengiriman Notifikasi Lokal
 */
object NotificationHelper {

    const val CHANNEL_ID = "kilau_order_channel"
    private const val CHANNEL_NAME = "Status Pesanan"

    /**
     * Membuat NotificationChannel (Diperlukan untuk Android 8.0/API 26+)
     */
    fun createChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notifikasi pembaruan status pesanan Kilau Laundry"
            }
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    /**
     * Menampilkan notifikasi lokal status pesanan
     */
    fun showOrderNotification(context: Context, title: String, message: String, notificationId: Int = 1001) {
        // Cek preferensi pengguna apakah notifikasi diaktifkan dari ProfilFragment
        val prefs = context.getSharedPreferences("user_pref", Context.MODE_PRIVATE)
        val isNotifEnabled = prefs.getBoolean("notif_enabled", true)
        if (!isNotifEnabled) return

        // Cek izin POST_NOTIFICATIONS untuk Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
                return
            }
        }

        // PendingIntent untuk membuka BaseActivity saat notifikasi diketuk
        val intent = Intent(context, BaseActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        NotificationManagerCompat.from(context).notify(notificationId, builder.build())
    }
}