package com.acarrell.urbandictionary.service

import com.acarrell.urbandictionary.model.DictionaryEntry
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ServiceController private constructor(
    private val apiHost: String,
    private val apiKey: String,
    private val baseUrl: String
) {
    private val udService: UDService
    fun getDefinitions(term: String): Single<List<DictionaryEntry>> {
        return udService.getDefinitions(term)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.entries }
    }

    private fun getClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.NONE)

        val clientInterceptor = Interceptor {
            val request = it.request().newBuilder()
                .addHeader(API_HOST_NAME, apiHost)
                .addHeader(API_KEY_NAME, apiKey)
                .build()
            it.proceed(request)
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(clientInterceptor)
            .build()
    }

    init {
        udService = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(getClient())
            .build()
            .create(UDService::class.java)
    }

    companion object {
        private const val API_HOST_NAME = "X-RapidAPI-Host"
        private const val API_KEY_NAME = "X-RapidAPI-Key"

        @JvmStatic
        fun create(apiHost: String, apiKey: String, baseUrl: String): ServiceController {
            return ServiceController(apiHost, apiKey, baseUrl)
        }
    }
}