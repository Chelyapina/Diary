package com.example.diary

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import com.example.diary.domain.DatabaseModule
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class DiaryApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}