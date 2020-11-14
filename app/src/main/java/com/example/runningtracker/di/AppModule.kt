package com.example.runningtracker.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import com.example.runningtracker.db.RunningDatabase
import com.other.Constants.DATABASE_NAME
import com.other.Constants.KEY_FIRST_TIME_TOGGLE
import com.other.Constants.KEY_NAME
import com.other.Constants.KEY_WEIGHT
import com.other.Constants.SHARED_PREFERENCES_NAME
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

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext
        app:Context
    )=app.getSharedPreferences(SHARED_PREFERENCES_NAME,MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideName(sharePref:SharedPreferences)=sharePref.getString(KEY_NAME,"") ?:""

    @Provides
    @Singleton
    fun provideWeight(sharePref:SharedPreferences)=sharePref.getFloat(KEY_WEIGHT,80f)

    @Provides
    @Singleton
    fun provideFirstTimeToggle(sharePref:SharedPreferences)=sharePref
        .getBoolean(KEY_FIRST_TIME_TOGGLE,true)

}