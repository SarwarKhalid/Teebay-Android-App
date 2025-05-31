package com.example.teebay.framework.network.di

import com.example.teebay.core.data.datasource.IProductsDataSourceRemote
import com.example.teebay.core.data.datasource.IUserDataSourceRemote
import com.example.teebay.framework.network.datasourceimpl.RetrofitProductDataSource
import com.example.teebay.framework.network.datasourceimpl.RetrofitUserDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Class to provide manual dependency injection for network interfaces
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class AbstractHiltNetworkModule {

    @Binds
    abstract fun bindUserDataSourceRemote(retrofitUserDatasource: RetrofitUserDataSource): IUserDataSourceRemote

    @Binds
    abstract fun bindProductsDataSourceRemote(retrofitProductDataSource: RetrofitProductDataSource): IProductsDataSourceRemote
}