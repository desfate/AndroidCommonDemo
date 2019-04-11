package com.defate.mac.androidcommondemo.rxjavas

import retrofit2.http.GET
import rx.Observable
import java.util.*

interface GetRequestInterfaces{

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20world")
    fun getCall() : Observable<Translation>

}