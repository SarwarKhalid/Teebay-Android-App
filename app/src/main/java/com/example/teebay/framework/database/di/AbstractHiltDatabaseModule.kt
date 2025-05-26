package com.example.teebay.framework.database.di

import com.example.teebay.core.data.datasource.IUserDataSourceLocal
import com.example.teebay.framework.database.datasourceimpl.RoomUserDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AbstractHiltDatabaseModule {

    @Binds
    abstract fun bindUserDataSourceLocal(roomUserDataSource: RoomUserDataSource): IUserDataSourceLocal
}