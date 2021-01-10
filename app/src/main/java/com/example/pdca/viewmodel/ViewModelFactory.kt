package com.example.pdca.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope

class ViewModelFactory_CycleList (
    private val coroutineScope: CoroutineScope,
    private val context: Context
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CycleListViewModel(
                coroutineScope,
                context.applicationContext as Application
        ) as T
    }
}

class ViewModelFactory_EditCycle(
        private val coroutineScope: CoroutineScope,
        private val context: Context
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EditCycleViewModel(
                coroutineScope,
                context.applicationContext as Application,
                context
        ) as T
    }
}