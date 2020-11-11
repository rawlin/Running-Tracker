package com.example.runningtracker.di

import android.content.Context
import androidx.room.Room
import com.example.runningtracker.db.RunningDatabase
import com.other.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRunningDatabase(
        @ApplicationContext
        app:Context
    )= Room.databaseBuilder(
        app,
        RunningDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideRunDao(
        db:RunningDatabase
    )=db.getRunDao()
}