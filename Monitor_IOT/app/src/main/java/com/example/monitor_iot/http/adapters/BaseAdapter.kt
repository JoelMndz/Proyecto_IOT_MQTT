package com.example.monitor_iot.http.adapters

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


open class BaseAdapter internal constructor(baseUrl: String) {
    private fun init(baseUrl: String) {
        if (retrofit == null) {
            retrofit = Retrofit.Builder().baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

    // Show HTTPS logs in dev mode
    private val client: OkHttpClient
        private get() {
            val builderClientHttp = OkHttpClient().newBuilder()
            // Show HTTPS logs in dev mode
            if (true) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = LEVEL_LOG
                builderClientHttp.addInterceptor(interceptor)
            }
            return builderClientHttp.build()
        }

    fun <T> createService(_class: Class<T>?): T {
        return retrofit!!.create(_class)
    }

    companion object {
        private var retrofit: Retrofit? = null
        private val LEVEL_LOG = HttpLoggingInterceptor.Level.BODY
    }

    init {
        init(baseUrl)
    }
}
