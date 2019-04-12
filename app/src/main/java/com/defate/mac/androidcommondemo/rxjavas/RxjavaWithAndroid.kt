package com.defate.mac.androidcommondemo.rxjavas

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.defate.mac.androidcommondemo.R
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_rxjava_android.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient



/**
 * 用于展示rxjava在android中应用的场景
 * https://blog.csdn.net/carson_ho/article/details/79168723
 */
class RxjavaWithAndroid : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rxjava_android)

        val client = OkHttpClient.Builder().build()

        start_btn.setOnClickListener {

        }


        val requestTime = 5;
        /**
         * 条件轮询
         */
        fun requestRequirement(){
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/") // 设置 网络请求 Url
                .client(client)
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                .build()
            val request = retrofit.create(GetRequestInterfaces::class.java)
            val observable = request.getCall()
            observable.repeatWhen(object: Function<Observable<Any>, ObservableSource<Any>>{
                override fun apply(t: Observable<Any>): ObservableSource<Any> {

                }
            })
        }



        /**
         * 无条件轮询
         */
        fun requestNoRequirement() {
            // 参数说明：
            // 参数1 = 第1次延迟时间；
            // 参数2 = 间隔时间数字；
            // 参数3 = 时间单位；
            Observable.interval(2, 1, TimeUnit.SECONDS)
                .doOnNext {
                    val retrofit: Retrofit = Retrofit.Builder()
                        .baseUrl("http://fy.iciba.com/") // 设置 网络请求 Url
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                        .build()

                    val request: GetRequestInterfaces = retrofit.create(GetRequestInterfaces::class.java)

                    val observable: Observable<Translation> = request.getCall()
                    System.out.println("第" + it + "次轮询")

                    observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : Observer<Translation> {
                            override fun onComplete() {

                            }

                            override fun onSubscribe(d: Disposable) {

                            }

                            override fun onNext(t: Translation) {
                                System.out.println(t)
                            }

                            override fun onError(e: Throwable) {
                                System.out.println(e.message)
                            }
                        })
                }.subscribe(object : Observer<Long> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(t: Long) {
                        System.out.println(t)
                    }

                    override fun onError(e: Throwable) {
                        System.out.println(e.message)
                    }
                })
        }
    }

}