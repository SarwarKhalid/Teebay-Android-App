package com.teebay.appname.framework.network.di

import com.teebay.appname.core.data.datasource.IProductsDataSourceRemote
import com.teebay.appname.core.data.datasource.IUserDataSourceRemote
import com.teebay.appname.framework.network.datasourceimpl.RetrofitProductDataSource
import com.teebay.appname.framework.network.datasourceimpl.RetrofitUserDataSource
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