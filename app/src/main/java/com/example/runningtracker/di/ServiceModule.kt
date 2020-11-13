package com.example.runningtracker.di

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.runningtracker.R
import com.example.runningtracker.ui.MainActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.other.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped

@Module
@InstallIn(ServiceComponent::class)
object ServiceModule {

    @Provides
    @ServiceScoped
    fun provideFusedLocationProviderClient(
            @ApplicationContext
            app:Context
    )=FusedLocationProviderClient(app)

    @Provides
    @ServiceScoped
    fun provideMainActivityPendingIntent(
            @ApplicationContext
            app:Context
    )= PendingIntent.getActivity(
            app,
            0,
            Intent(app, MainActivity::class.java).also {
                it.action= Constants.ACTION_SHOW_TRACKING_FRAGMENT
            },
            PendingIntent.FLAG_UPDATE_CURRENT // When pending intent already exists, it will update it instead of recreating it
    )

    @Provides
    @ServiceScoped
    fun provideBaseNotificationBuilder(
            @ApplicationContext
            app:Context,
            pendingIntent: PendingIntent
    )= NotificationCompat.Builder(app, Constants.NOTIFICATION_CHANNEL_ID)
            .setAutoCancel(false) // Prevents Notification to be canceled on user click
            .setOngoing(true) // Notification can't be swiped away
            .setSmallIcon(R.drawable.ic_directions_run_black_24dp)
            .setContentTitle("Running Tracker")
            .setContentText("00:00:00")
            .setContentIntent(pendingIntent)


}