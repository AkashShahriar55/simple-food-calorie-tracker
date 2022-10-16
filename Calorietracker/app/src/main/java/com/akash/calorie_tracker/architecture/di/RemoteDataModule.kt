package com.akash.calorie_tracker.architecture.di

import com.akash.calorie_tracker.data.api.ClientApi
import com.akash.calorie_tracker.data.api.AdminApi
import com.akash.calorie_tracker.data.api.LoginApi
import com.akash.calorie_tracker.data.repositories.datasource.ClientDataSource
import com.akash.calorie_tracker.data.repositories.datasource.AdminDataSource
import com.akash.calorie_tracker.data.repositories.datasource.LoginDataSource
import com.akash.calorie_tracker.data.repositories.datasourceimpl.ClientDataSourceImpl
import com.akash.calorie_tracker.data.repositories.datasourceimpl.AdminDataSourceImpl
import com.akash.calorie_tracker.data.repositories.datasourceimpl.LoginDatasourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {

    @Provides
    @Singleton
    fun provideFoodDataSource(foodApi: AdminApi): AdminDataSource {
        return AdminDataSourceImpl(foodApi)
    }

    @Provides
    @Singleton
    fun provideLoginDataSource(loginApi: LoginApi): LoginDataSource {
        return LoginDatasourceImpl(loginApi)
    }


    @Provides
    @Singleton
    fun provideClientDataSource(clientApi: ClientApi): ClientDataSource {
        return ClientDataSourceImpl(clientApi)
    }
}