package com.defate.mac.androidcommondemo.samples.room

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

/**
 * Scp 实体类
 *
 * tableName：设置表名字。默认是类的名字。
 * indices：设置索引。
 * inheritSuperIndices：父类的索引是否会自动被当前类继承。
 * primaryKeys：设置主键。
 * foreignKeys：设置外键。
 *
 * @Entity(primaryKeys = {"firstName","lastName"})   复合主键也可以这样写
 *
 *
 * 索引：
 * 数据库索引用于提高数据库表的数据访问速度的。数据库里面的索引有单列索引和组合索引。Room里面可以通过@Entity的indices属性来给表格添加索引。
 * 唯一索引就想主键一样重复会报错的。可以通过的@Index的unique数学来设置是否唯一索引
 *
 * 全文搜索：
 * 应用程序需要通过全文搜索（FTS）快速访问数据库信息  要使用Room 2.1.0及更高版本中提供的此功能
 * @Fts4
 */

//加入索引功能

//@Entity(tableName = "tb_scp")

//仅当您的应用程序具有严格的磁盘空间要求或者您有时，才使用`@ Fts3`
//需要与较旧的SQLite版本兼容。


@Entity(tableName = "tb_scp", indices = arrayOf(Index(value = ["scp_code","scp_name"], unique = true)))//通过code和name索引
class Scp(
    @PrimaryKey(autoGenerate = true) var id: Int?,  //自增模式
    @ColumnInfo(name = "scp_code") var scpCode: String?,
    @ColumnInfo(name = "scp_name") var scpName: String?,
    @ColumnInfo(name = "scp_content") var scpContent: String?
)