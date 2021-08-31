package com.test.restaurantapp.networkservice

import com.google.gson.JsonArray
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import com.google.gson.JsonObject
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.FieldMap
import retrofit2.http.GET

interface ApiService {
    @GET("test")
    fun request(): Observable<Response<JsonArray?>?>?
}