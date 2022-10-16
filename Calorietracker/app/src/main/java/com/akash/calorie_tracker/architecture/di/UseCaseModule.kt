package com.akash.calorie_tracker.architecture.di

import com.akash.calorie_tracker.domain.repositories.AdminRepository
import com.akash.calorie_tracker.domain.repositories.LoginRepository
import com.akash.calorie_tracker.domain.usecases.FoodUserUserCase
import com.akash.calorie_tracker.domain.usecases.LoginUseCases

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideSearchUseCase(
        adminRepository: AdminRepository
    ): FoodUserUserCase =
        FoodUserUserCase(adminRepository)


    @Provides
    fun provideLoginUseCase(
        loginRepository: LoginRepository
    ): LoginUseCases =
        LoginUseCases(loginRepository)

}