package ru.vtinch.rickandmortyexplorer.search.data.cloud

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

interface CloudModule<T : Any> {

    fun service(): T

    class Base(apiUrl: String) : CloudModule<CharacterService> {

        private val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            })
            .readTimeout(HTTP_CLIENT_READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(HTTP_CLIENT_WRITE_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(HTTP_CLIENT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()

        private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(apiUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        override fun service(): CharacterService {
            return retrofit.create(CharacterService::class.java)
        }
    }

    companion object {
        private const val HTTP_CLIENT_READ_TIMEOUT = 60L
        private const val HTTP_CLIENT_WRITE_TIMEOUT = 60L
        private const val HTTP_CLIENT_CONNECT_TIMEOUT = 60L
    }
}