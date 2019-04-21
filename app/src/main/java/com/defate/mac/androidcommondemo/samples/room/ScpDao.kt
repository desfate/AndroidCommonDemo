package com.defate.mac.androidcommondemo.samples.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface ScpDao{
    @Query("SELECT * FROM scp")
    fun getAll(): List<Scp>

//    @Query("SELECT * FROM tb_scp scpName LIKE :names AND " + "scpCode LIKE :codes LIMIT 1")
//    fun findByName(names: String, codes: String): LiveData<Scp>

//    @Insert
//    fun insertAll(vararg scps: Scp) //vararg 可变参数 类似于java里的。。。

    @Insert
    fun insertAll(scps: List<Scp>) //vararg 可变参数 类似于java里的。。。

    @Delete
    fun delete(scp: Scp)
}