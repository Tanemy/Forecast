package com.example.cityweather.di

import com.example.cityweather.di.qualifiers.UIScheduler
import com.example.cityweather.di.qualifiers.WorkScheduler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@InstallIn(SingletonComponent::class)
@Module
object SchedulersModule {

    @Provides
    @UIScheduler
    fun providesUIScheduler(): Scheduler = AndroidSchedulers.mainThread()

    @Provides
    @WorkScheduler
    fun providesWorkScheduler(): Scheduler = Schedulers.io()
}
