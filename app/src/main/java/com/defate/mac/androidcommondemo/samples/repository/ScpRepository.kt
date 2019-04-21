package com.defate.mac.androidcommondemo.samples.repository

import com.defate.mac.androidcommondemo.samples.room.Scp
import com.defate.mac.androidcommondemo.samples.room.ScpDao

/**
 * 用于管理数据对外的储存类
 * 本身是单例模式
 *     // For Singleton instantiation
 */
class ScpRepository private constructor(private val dao: ScpDao){

    fun getScp() = dao.getAll()
//    fun getByName(name: String, code: String) = dao.findByName(name, code)
    fun insert(scp: List<Scp>) = dao.insertAll(scp)

    companion object {
        @Volatile private var instance: ScpRepository? = null
        fun getInstance(dao: ScpDao) = instance ?: synchronized(this) {
            instance ?: ScpRepository(dao).also { instance = it }
        }
        // ?:操作符，如果 ?: 左侧表达式非空，就返回其左侧表达式，否则返回右侧表达式。 请注意，当且仅当左侧为空时，才会对右侧表达式求值。
    }
    }