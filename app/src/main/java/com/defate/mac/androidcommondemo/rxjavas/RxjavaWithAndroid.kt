package com.defate.mac.androidcommondemo.rxjavas

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.defate.mac.androidcommondemo.R
import kotlinx.android.synthetic.main.activity_rxjava_android.*
import rx.Observable
import rx.functions.Action1
import java.util.concurrent.TimeUnit
import java.util.function.Consumer

/**
 * 用于展示rxjava在android中应用的场景
 * https://blog.csdn.net/carson_ho/article/details/79168723
 */
class RxjavaWithAndroid: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rxjava_android)

        start_btn.setOnClickListener {
            // 参数说明：
            // 参数1 = 第1次延迟时间；
            // 参数2 = 间隔时间数字；
            // 参数3 = 时间单位；
//            Observable.interval(2,1,TimeUnit.SECONDS)
//                .doOnNext(
//                    object: Consumer<Long>(){
//
//                    }
//                })


        }
    }

}