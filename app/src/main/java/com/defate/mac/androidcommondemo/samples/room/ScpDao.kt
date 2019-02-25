package com.defate.mac.androidcommondemo.samples.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface ScpDao{
    @Query("SELECT * FROM tb_scp")
    fun getAll(): List<Scp>

    @Query("SELECT * FROM tb_scp WHERE id IN (:sids)")
    fun loadAllByIds(sids: IntArray): List<Scp>

    @Query("SELECT * FROM tb_scp scpName LIKE :names AND " + "scpCode LIKE :codes LIMIT 1")
    fun findByName(names: String, codes: String): Scp

//    @Insert
//    fun insertAll(vararg scps: Scp) //vararg 可变参数 类似于java里的。。。

    @Insert
    fun insertAll(scps: List<Scp>) //vararg 可变参数 类似于java里的。。。

    @Delete
    fun delete(scp: Scp)
}