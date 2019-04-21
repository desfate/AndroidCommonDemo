package com.defate.mac.androidcommondemo.rxjavas

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.defate.mac.androidcommondemo.R
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_rxjava_android.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import io.reactivex.functions.Function3


import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.IOException


/**
 * 用于展示rxjava在android中应用的场景
 * https://blog.csdn.net/carson_ho/article/details/79168723
 */
class RxjavaWithAndroid : AppCompatActivity() {


    private val client = OkHttpClient.Builder().build()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rxjava_android)
        submitChecker()
        clickProtect() //防抖？
        searchOptimization()
        start_btn.setOnClickListener {
            submitChecker()
        }
    }

    var requestTime = 5  //条件轮训次数
    /**
     * 条件轮询  需要连续请求某一数据  若干次
     */
    private fun requestRequirement() {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://fy.iciba.com/") // 设置 网络请求 Url
            .client(client)
            .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
            .build()
        val request = retrofit.create(GetRequestInterfaces::class.java)
        val observable = request.getCall()
        observable.repeatWhen { t ->
            // 将原始 Observable 停止发送事件的标识（Complete（） /  Error（））转换成1个 Object 类型数据传递给1个新被观察者（Observable）
            // 以此决定是否重新订阅 & 发送原来的 Observable，即轮询
            // 此处有2种情况：
            // 1. 若返回1个Complete（） /  Error（）事件，则不重新订阅 & 发送原来的 Observable，即轮询结束
            // 2. 若返回其余事件，则重新订阅 & 发送原来的 Observable，即继续轮询
            t.flatMap {
                // 加入判断条件：当轮询次数 = 5次后，就停止轮询
                if (requestTime == 0) {
                    // 此处选择发送onError事件以结束轮询，因为可触发下游观察者的onError（）方法回调
                    Observable.error(Throwable("error"))
                } else Observable.just(1).delay(2000, TimeUnit.MILLISECONDS)
            }
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Translation> {
                override fun onComplete() {
                    System.out.println("onComplete")
                }

                override fun onSubscribe(d: Disposable) {
                    System.out.println(d)
                }

                override fun onNext(t: Translation) {
                    System.out.println(t)
                    requestTime--
                }

                override fun onError(e: Throwable) {
                    System.out.println(e)
                }
            })
    }

    /**
     * 无条件轮询  需要一直请求某一数据
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

    private var currentRetryCount = 0  //当前尝试次数
    private var maxConnectCount = 5   //总尝试次数
    /**
     * 此次请求不成功  进行重新请求
     */
    @SuppressLint("CheckResult")
    private fun requestNetError() {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://fy.iciba.com/") // 设置 网络请求 Url
            .client(client)
            .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
            .build()
        val request = retrofit.create(GetRequestInterfaces::class.java)
        val observable = request.getCall()
        observable.retryWhen { t ->
            t.flatMap {
                if(it is IOException){
                    if(currentRetryCount < maxConnectCount){
                        currentRetryCount ++
                        System.out.println( "重试次数 = " + currentRetryCount);
                        val waitRetryTime: Long = (1000 + currentRetryCount* 1000).toLong()
                        Observable.just(1).delay(waitRetryTime, TimeUnit.MILLISECONDS)
                    }else{
                        Observable.error(Throwable("重试次数已超过设置次数 = " +currentRetryCount  + "，即 不再重试"))
                    }
                }else{
                    Observable.error(Throwable("发生了非网络异常（非I/O异常）"))
                }
            }
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: Observer<Translation>{
                override fun onComplete() {
                    System.out.println("onComplete")
                }

                override fun onSubscribe(d: Disposable) {
                    System.out.println(d)
                }

                override fun onNext(t: Translation) {
                    System.out.println(t)
                }

                override fun onError(e: Throwable) {
                    System.out.println(e)
                }
            })

    }

    /**
     * 网络请求嵌套回调
     */
    @SuppressLint("CheckResult")
    private fun requestWithCallBack(){
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://fy.iciba.com/") // 设置 网络请求 Url
            .client(client)
            .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
            .build()
        val request = retrofit.create(GetRequestInterfaces::class.java)
        val observableRegister = request.getRegister()
        val observableLogin = request.getLogin()

        observableRegister.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                System.out.println(it)
            }.doOnError {
                System.out.println(it)
            }
            .observeOn(Schedulers.io())   // （新被观察者，同时也是新观察者）切换到IO线程去发起登录请求
            // 特别注意：因为flatMap是对初始被观察者作变换，所以对于旧被观察者，它是新观察者，所以通过observeOn切换线程
            // 但对于初始观察者，它则是新的被观察者
            .flatMap { observableLogin }
            .observeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { t -> System.out.println(t) },
                { System.out.println("登录失败"); })
    }

    /**
     * 从数据源中获取数据
     */
    private fun requestDataSource(){

    }

    /**
     * 合并两套数据
     */
    @SuppressLint("CheckResult")
    private fun requestZipSource(){
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://fy.iciba.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        val request = retrofit.create(GetRequestInterfaces::class.java)
        val observableRegister = request.getRegister().subscribeOn(Schedulers.io())
        val observableLogin = request.getLogin().subscribeOn(Schedulers.io())
        Observable.zip(observableRegister, observableLogin, object: BiFunction<RegisterData, LoginData, String>{
            override fun apply(t1: RegisterData, t2: LoginData): String {
                return t1.toString() + t2.toString()
            }
        }).observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { t -> System.out.println(t) },
                { t -> System.out.println(t) })
    }

    /**
     * 联合判断
     */
    @SuppressLint("CheckResult")
    private fun submitChecker(){
        val accountObservblbe :Observable<CharSequence> = RxTextView.textChanges(account_edt).skip(1)
        val passwordObservble :Observable<CharSequence> = RxTextView.textChanges(password_edt).skip(1)
        val ageObservable :Observable<CharSequence> = RxTextView.textChanges(age_edt).skip(1)

        Observable.combineLatest(accountObservblbe, passwordObservble, ageObservable,
            Function3<CharSequence, CharSequence, CharSequence, Boolean> { t1, t2, t3 ->
                val isName = t1.isNotEmpty()
                val isPassword = t2.isNotEmpty()
                val isAge = t3.isNotEmpty()
                isName && isPassword && isAge
            }).subscribe { t ->
                submit_btn.isEnabled = t!! }
    }

    /**
     * 功能防抖
     */
    private fun clickProtect(){
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://fy.iciba.com/") // 设置 网络请求 Url
            .client(client)
            .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
            .build()

        val request = retrofit.create(GetRequestInterfaces::class.java)


        RxView.clicks(protect_btn)
            .throttleFirst(2, TimeUnit.SECONDS)
            .subscribe(object: Observer<Any>{
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: Any) {
                    System.out.println("发送网络请求")
                    request.getCall() //发送网络请求
                }

                override fun onError(e: Throwable) {
                    System.out.println(e)
                }

            })
    }

    /**
     * 搜索请求优化
     */
    private fun searchOptimization(){
        RxTextView.textChanges(input_edt)
            .debounce (1, TimeUnit.SECONDS).skip(1)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: Observer<CharSequence>{
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: CharSequence) {
                    search_tv.setText("发送给服务器的字符 = " + t.toString());
                }

                override fun onError(e: Throwable) {

                }

            })

    }

}