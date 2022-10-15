package com.akash.calorie_tracker.architecture.di



import android.util.Log
import com.akash.calorie_tracker.BASE_URL
import com.akash.calorie_tracker.architecture.manager.SessionManager
import com.akash.calorie_tracker.data.api.ClientApi
import com.akash.calorie_tracker.data.api.FoodApi
import com.akash.calorie_tracker.data.api.LoginApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@ExperimentalSerializationApi
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideHttpClient(sessionManager: SessionManager): OkHttpClient {

        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(Interceptor {
                val newRequest: Request = it.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${sessionManager.getAuthToken()}")
                    .build()
                it.proceed(newRequest)
            })
            .build()
    }


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val contentType = MediaType.get("application/json")
        val json = Json {
            ignoreUnknownKeys = true
        }

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideFoodApi(retrofit: Retrofit): FoodApi {
        return retrofit.create(FoodApi::class.java)
    }


    @Provides
    @Singleton
    fun provideLoginApi(retrofit: Retrofit): LoginApi {
        return retrofit.create(LoginApi::class.java)
    }



    @Provides
    @Singleton
    fun provideClientApi(retrofit: Retrofit): ClientApi {
        return retrofit.create(ClientApi::class.java)
    }



}