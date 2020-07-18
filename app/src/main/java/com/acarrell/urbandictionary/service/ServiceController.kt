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

class ServiceController private constructor() {
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
                .addHeader("X-RapidAPI-Host", "mashape-community-urban-dictionary.p.rapidapi.com")
                .addHeader("X-RapidAPI-Key", "502392b060msh11f0d8714789cdcp1e60a6jsnbc26ba74ee55")
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
            .baseUrl("https://mashape-community-urban-dictionary.p.rapidapi.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(getClient())
            .build()
            .create(UDService::class.java)
    }

    companion object {
        @JvmStatic
        fun create(): ServiceController {
            return ServiceController()
        }
    }
}