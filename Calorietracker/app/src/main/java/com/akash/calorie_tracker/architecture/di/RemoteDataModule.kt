package com.akash.calorie_tracker.architecture.di

import com.akash.calorie_tracker.data.api.FoodApi
import com.akash.calorie_tracker.data.api.LoginApi
import com.akash.calorie_tracker.data.repositories.datasource.FoodDataSource
import com.akash.calorie_tracker.data.repositories.datasource.LoginDataSource
import com.akash.calorie_tracker.data.repositories.datasourceimpl.FoodDataSourceImpl
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
    fun provideFoodDataSource(foodApi: FoodApi): FoodDataSource {
        return FoodDataSourceImpl(foodApi)
    }

    @Provides
    @Singleton
    fun provideLoginDataSource(loginApi: LoginApi): LoginDataSource {
        return LoginDatasourceImpl(loginApi)
    }
}