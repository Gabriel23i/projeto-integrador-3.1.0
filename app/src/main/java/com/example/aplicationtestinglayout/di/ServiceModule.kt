package com.example.aplicationtestinglayout.di

import android.content.Context
import com.example.aplicationtestinglayout.data.TarefaDao
import com.example.aplicationtestinglayout.data.TarefaDataBase
import com.example.cardview.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideDao(@ApplicationContext context: Context): TarefaDao{
        return TarefaDataBase.getDataBase(context).TarefaDao()
    }

    @Provides
    @Singleton

    fun provideTaskService (tarefaDao: TarefaDao): Repository {
        return Repository(tarefaDao)
    }

}