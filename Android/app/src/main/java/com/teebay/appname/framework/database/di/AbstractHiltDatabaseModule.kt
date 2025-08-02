package com.teebay.appname.framework.database.di

import com.teebay.appname.core.data.datasource.ITokenDataSource
import com.teebay.appname.core.data.datasource.IUserDataSourceLocal
import com.teebay.appname.framework.database.datasourceimpl.RoomUserDataSource
import com.teebay.appname.framework.database.datasourceimpl.TokenDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AbstractHiltDatabaseModule {

    @Binds
    abstract fun bindUserDataSourceLocal(roomUserDataSource: RoomUserDataSource): IUserDataSourceLocal

    @Binds
    abstract fun bindTokenDataSource(tokenDataSource: TokenDataSourceImpl): ITokenDataSource
}