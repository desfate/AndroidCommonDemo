package com.defate.mac.androidcommondemo.samples.adapter

import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class MViewHolder(view: View) : RecyclerView.ViewHolder(view){
    lateinit var mViews: SparseArray<View>
    lateinit var mConvertView: View

    init {
         mViews = SparseArray()
         mConvertView= view
    }

    constructor(view: View, views: SparseArray<View>): this(view){
        mViews = views
        mConvertView= view
    }

    companion object {
        fun getViewHolde(parent: ViewGroup, layoutId: Int): MViewHolder{
            return MViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent,false))
        }
    }

    fun <T : View> getView(id: Int): T?{
        mViews?.get(id).let {
            mViews.put(id, mConvertView.findViewById(id))
        }
        return mViews.get(id) as T?
    }
}