package com.example.pdca.roomdata

import androidx.room.*

@Dao// @DaoアノテーションでDaoであることを表す
interface CycleDao{
    @Query("SELECT * from cycledata")// 全件取得のSQLをかく。この時にソートや抽出などもできる。
    fun getAllData(): List<CycleData>

    @Insert(onConflict=OnConflictStrategy.REPLACE)// 追加処理 コンフリクトが起こった時は置き換える
    fun insert(cycledata: CycleData)

    @Update
    fun update(cycledata: CycleData)

    @Delete
    fun delete(cycledata: CycleData)
}