package com.codecool.drawapp.dependency.random_word

import android.util.Log
import com.codecool.drawapp.api.ApiRequests
import com.codecool.drawapp.api.ApiSingleton
import com.codecool.drawapp.api.RandomWord
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.internal.observers.ConsumerSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RandomWordImplementation : RandomWordService {

    interface RandomWordCallback{
        fun onSuccess(word : RandomWord)
        fun onError()
    }

    override fun getRandomWord(view : RandomWordCallback)  {
        val retrofit = ApiSingleton.getApiClient()
        val myApi = retrofit.create(ApiRequests::class.java)

        val results : Observable<RandomWord> = myApi
            .getRandomWord()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        results
            .subscribe(object : Observer<RandomWord>{
                override fun onSubscribe(d: Disposable) {}

                override fun onNext(t: RandomWord) {
                    view.onSuccess(t)
                    Log.d("RandomWordImplementation", "OnNext() -> ${t.name}")
                }

                override fun onError(e: Throwable) {
                    Log.d("RandomWordImplementation", "onError() -> ${e.message}")
                }

                override fun onComplete() {
                    Log.d("RandomWordImplementation", "onComplete()")
                }
            })
    }
}