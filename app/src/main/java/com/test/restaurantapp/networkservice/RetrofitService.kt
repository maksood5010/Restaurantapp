package com.test.restaurantapp.networkservice

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.gson.JsonArray
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitService {
    var retrofit: Retrofit? = null
    var service = buildRetrofit()
    fun buildRetrofit(): ApiService {
        if (!httpClient.interceptors().isEmpty()) {
            httpClient.interceptors().clear()
        }
        httpClient.readTimeout(60, TimeUnit.SECONDS)
        httpClient.connectTimeout(60, TimeUnit.SECONDS)
        httpClient.addInterceptor(setLogger())
        httpClient.addInterceptor(Interceptor { chain: Interceptor.Chain ->
            val original = chain.request()
            val requestBuilder: Request.Builder = original.newBuilder()
                .method(original.method, original.body)
            val request: Request = requestBuilder.build()
            chain.proceed(request)
        })
        retrofit = Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .client(httpClient.build())
            .build()
        return retrofit!!.create<ApiService>(ApiService::class.java)
    }

    fun getRequest(): Observable<Response<JsonArray?>?>? {

        return service.request()
    }

    companion object {
        private const val SERVER_URL= "https://gateway-dev.shisheo.com/social/api/web/post/arina/"
        private val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        fun setLogger(): HttpLoggingInterceptor {
            val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            return logging
        }

        fun isConnected(context: Context): Boolean {
            val connectivityManager: ConnectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo: NetworkInfo? = connectivityManager.getActiveNetworkInfo()
            return activeNetworkInfo != null && activeNetworkInfo.isConnected()
        }
    }
}