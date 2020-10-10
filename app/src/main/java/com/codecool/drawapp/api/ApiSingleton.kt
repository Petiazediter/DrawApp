package com.codecool.drawapp.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiSingleton{
    companion object {
        private var instance : Retrofit? = null
        fun getApiClient () : Retrofit {
            instance?.let{
                return it
            } ?: run{

                val client = OkHttpClient.Builder()
                    .build()

                instance = Retrofit.Builder()
                    .baseUrl("https://api.wordnik.com/v4/words.json/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build()
                return instance!!
            }
        }
    }
}