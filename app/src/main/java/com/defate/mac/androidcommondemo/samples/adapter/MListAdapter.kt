package com.defate.mac.androidcommondemo.samples.adapter

import android.support.v7.widget.RecyclerView
import android.text.Layout
import android.view.ViewGroup

typealias ConvertCallBack<T> = (holder: MViewHolder, data: T, position: Int) -> Unit
typealias LayoutCallBack = (layoutId: Int) -> Int

class MListAdapter<T>(var items: List<T>, layoutCallBack: LayoutCallBack ?= null, convertCallBack: ConvertCallBack<T> ?= null) : RecyclerView.Adapter<MViewHolder> (){

    var layoutCallBacks: LayoutCallBack = layoutCallBack!!
    var convertCallBacks: ConvertCallBack<T> = convertCallBack!!

    override fun onBindViewHolder(p0: MViewHolder, p1: Int) {
        convertCallBacks?.invoke(p0, items.get(p1), p1)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MViewHolder {
        return MViewHolder.getViewHolde(p0,layoutCallBacks?.invoke(p1))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setData(list: List<T>){
        items = list
    }
}