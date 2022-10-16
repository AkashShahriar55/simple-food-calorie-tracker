package com.akash.calorie_tracker.architecture.di

import com.akash.calorie_tracker.architecture.manager.SessionManager
import com.akash.calorie_tracker.data.repositories.ClientRepositoryImpl
import com.akash.calorie_tracker.data.repositories.AdminRepositoryImpl
import com.akash.calorie_tracker.data.repositories.LoginRepositoryImpl
import com.akash.calorie_tracker.data.repositories.datasource.ClientDataSource
import com.akash.calorie_tracker.data.repositories.datasource.AdminDataSource
import com.akash.calorie_tracker.data.repositories.datasource.LoginDataSource
import com.akash.calorie_tracker.domain.repositories.ClientRepository
import com.akash.calorie_tracker.domain.repositories.AdminRepository
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
        adminDataSource: AdminDataSource,
        sessionManager: SessionManager
    ): AdminRepository =
        AdminRepositoryImpl(adminDataSource,sessionManager)



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