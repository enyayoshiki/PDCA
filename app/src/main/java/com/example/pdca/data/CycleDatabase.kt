package com.example.pdca.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities=arrayOf(CycleData::class),version=1)// Databaseの内容を@Databaseで設定。UserクラスをDBに保存する。versionはデータの構造に変更があれば番号を上げていきます。
abstract class CycleDatabase: RoomDatabase(){
    abstract fun pdcaCycleDao(): CycleDao// cycleデータを取り扱うDAO



    companion object{
        @Volatile
        private var INSTANCE: CycleDatabase?=null

//        private val migration1to2 = object : Migration(1, 2) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL("CREATE TABLE IF NOT EXISTS " +
//                        "`baseId` (`baseId` INTEGER NON NULL, PRYMARY KEY (`cycleId`)) "
////                        "`user_account_temporary` (`id` INTEGER NOT NULL, `state` INTEGER NOT NULL, `role` TEXT NOT NULL, `username` TEXT NOT NULL, PRIMARY KEY(`id`))" +
////                "baseId=Column{name='baseId', type='INTEGER', affinity='3', notNull=true, primaryKeyPosition=0, defaultValue='null'}"
//                )
//
//            }
//        }

        fun getDatabase(context: Context): CycleDatabase {
            val tempInstance= INSTANCE
            if(tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance= Room.databaseBuilder(
                    context.applicationContext,
                    CycleDatabase::class.java,
                    "cycle.db")
//                        .addMigrations(migration1to2)
                        .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}

