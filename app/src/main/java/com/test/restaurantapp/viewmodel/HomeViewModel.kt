package com.maqader.restaurantapp.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonArray
import com.test.restaurantapp.networkservice.RetrofitService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private  var liveData = MutableLiveData<Response<JsonArray?>>()
    fun getLiveData(): LiveData<Response<JsonArray?>> {
        return liveData
    }

    fun getData(context: Context) {
        val service = RetrofitService()
        val responseObservable: Observable<Response<JsonArray?>?>? = service.getRequest()
        responseObservable?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : DisposableObserver<Response<JsonArray?>?>() {
            override fun onNext(JsonArrayResponse: Response<JsonArray?>) {
                if (JsonArrayResponse.isSuccessful()) {
                    liveData.postValue(JsonArrayResponse)
                } else {
                    Log.e("TAG", "onFailure: at " + JsonArrayResponse.errorBody())
                    Toast.makeText(context, "Sorry Something Went wrong", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onError(e: Throwable) {
                Log.e("TAG", "onFailure: at " + e.localizedMessage)
                Toast.makeText(context,"Sorry Something Went wrong", Toast.LENGTH_SHORT)
                    .show()
                liveData.postValue(null)
            }

            override fun onComplete() {}
        })

    }

}
