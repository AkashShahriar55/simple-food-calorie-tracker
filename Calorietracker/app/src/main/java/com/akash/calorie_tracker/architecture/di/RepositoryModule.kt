package com.akash.calorie_tracker.architecture.di

import com.akash.calorie_tracker.architecture.manager.SessionManager
import com.akash.calorie_tracker.data.repositories.ClientRepositoryImpl
import com.akash.calorie_tracker.data.repositories.FoodRepositoryImpl
import com.akash.calorie_tracker.data.repositories.LoginRepositoryImpl
import com.akash.calorie_tracker.data.repositories.datasource.ClientDataSource
import com.akash.calorie_tracker.data.repositories.datasource.FoodDataSource
import com.akash.calorie_tracker.data.repositories.datasource.LoginDataSource
import com.akash.calorie_tracker.domain.repositories.ClientRepository
import com.akash.calorie_tracker.domain.repositories.FoodRepository
import com.akash.calorie_tracker.domain.repositories.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideFoodRepository(
        foodDataSource: FoodDataSource
    ): FoodRepository =
        FoodRepositoryImpl(foodDataSource)



    @Provides
    fun provideLoginRepository(
        loginDataSource: LoginDataSource,
        sessionManager: SessionManager
    ): LoginRepository =
        LoginRepositoryImpl(loginDataSource,sessionManager)

    @Provides
    fun provideClientRepository(
        clientDataSource: ClientDataSource,
        sessionManager: SessionManager
    ): ClientRepository =
        ClientRepositoryImpl(clientDataSource,sessionManager)




}