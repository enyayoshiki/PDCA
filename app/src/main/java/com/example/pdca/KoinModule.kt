package com.example.pdca

import androidx.room.Room
import com.example.pdca.data.CycleDatabase
import com.example.pdca.viewmodels.TestEditCycleViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

object KoinModule {

//    fun appModule() = module {
//        // Databaseインスタンスをシングルトンで生成
//        single { Room.databaseBuilder(androidContext(), CycleDatabase::class.java, "xxx.db").build() }
//    }
//
//    fun messageModule() = module {
//        // Daoを生成
//        factory { get<CycleDatabase>().pdcaCycleDao() }
//        // viewModelのinjection
//        viewModel { TestEditCycleViewModel(get()) }
//        // repositoryのinjection
//        single { CycleDataRepository(get()) }
//    }
}