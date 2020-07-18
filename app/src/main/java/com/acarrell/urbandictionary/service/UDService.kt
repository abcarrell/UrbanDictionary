package com.acarrell.urbandictionary.service

import com.acarrell.urbandictionary.model.DictionaryEntry
import com.acarrell.urbandictionary.model.UDResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface UDService {
    @GET("define")
    fun getDefinitions(
        @Query("term") term: String?
    ): Single<UDResponse>
}