package com.acarrell.urbandictionary.service

import android.app.Application
import com.acarrell.urbandictionary.model.DictionaryEntry
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

class ServiceController private constructor(
    private val application: Application,
    private val apiHost: String,
    private val apiKey: String,
    baseUrl: String
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

        val cacheInterceptor = Interceptor {
            val response = it.proceed(it.request())
            val cacheControl = CacheControl.Builder()
                .maxAge(15, TimeUnit.MINUTES)
                .build()

            response.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Cache-Control")
                .header("Cache-Control", cacheControl.toString())
                .build()
        }
        val cacheDirectory = File(application.applicationContext.cacheDir, "http-cache")
        val cache = Cache(cacheDirectory, 10 * 1024 * 1024)

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(clientInterceptor)
            .addInterceptor(cacheInterceptor)
            .cache(cache)
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
        fun create(application: Application, apiHost: String, apiKey: String, baseUrl: String): ServiceController {
            return ServiceController(application, apiHost, apiKey, baseUrl)
        }
    }
}