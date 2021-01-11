package com.example.pdca.viewmodels

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import androidx.databinding.ObservableBoolean
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.*
import com.example.pdca.CycleDataRepository
import com.example.pdca.R
import com.example.pdca.activities.MainActivity
import com.example.pdca.application.PdcaApplication
import com.example.pdca.extention.showToast
import com.example.pdca.fragments.AddCycleDialogFragment
import com.example.pdca.data.CycleData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class TestEditCycleViewModel : ViewModel() {

    private val cycleDataRepository = CycleDataRepository()

    var cycleData = MutableLiveData<CycleData>()
    var isLoading = false
    var isNext = false

    fun setCycleData(id: Int, number: Int) {
        Timber.i("viewModel setCycleData")
        if (!isLoading) {
            viewModelScope.launch {
                val cycleDataList = cycleDataRepository.loadCycleData()
                cycleDataList.filter { it.cycleid == id && it.number_of_cycle == number }
                    .forEach {
                        cycleData.postValue(it)
                    }
            }
            isLoading = true
        }
    }

    fun updateCycleDate() {
        val editCycleData = cycleData.value ?: CycleData()
        viewModelScope.launch {
            cycleDataRepository.updataCycleData(editCycleData)
        }
    }

    fun nextCycle() {
        val editCycle = cycleData.value ?: CycleData()
        //(finishcycleを1にして完了したことにする)
        editCycle.finishcycle++
        viewModelScope.launch {
            cycleDataRepository.updataCycleData(editCycle)
        }
    }

    fun postValue(){
        val editCycle = cycleData.value ?: CycleData()
        cycleData.postValue(editCycle)
    }
}


