package com.defate.mac.androidcommondemo.rxjavas

import io.reactivex.Observable
import retrofit2.http.GET


interface GetRequestInterfaces{

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20world")
    fun getCall() : Observable<Translation>


    // 网络请求1
    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20register")
    fun getRegister(): Observable<RegisterData>

    // 网络请求2
    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20login")
    fun getLogin(): Observable<LoginData>


}

