package com.example.pdca.viewmodels

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pdca.CycleDataRepository
import com.example.pdca.data.CycleData
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.math.hypot

class ContentsViewModel : ViewModel() {

    private val cycleDataRepository = CycleDataRepository()

    var cycleDataList = listOf<CycleData>()
    var isLoading = false

    var contentsCycleData = MutableLiveData<CycleData>().apply {
        value = CycleData()
    }
    var filterCycleDataList = MutableLiveData<MutableList<CycleData>>().apply {
        value = mutableListOf()
    }



    fun setCycleData(id: Int) {
        if (!isLoading) {
            viewModelScope.launch {
                cycleDataList = cycleDataRepository.loadCycleData()
                cycleDataList.filter { it.cycleid == id }
                    .forEach { cycleData ->
                        contentsCycleData.postValue(cycleData)

                        if (cycleData.number_of_cycle == 1 && cycleData.finishcycle == 0) {
                            return@launch
                        } else {
                            filterCycleDataList.postValue(cycleDataList.filter { it.baseId == cycleData.baseId } as MutableList)
                        }
                    }
            }
            isLoading = true
        }
    }

    fun changeCycle(type: String) {
        var numberCycle = contentsCycleData.value?.number_of_cycle ?: 0
        Timber.i("contents numberCycle start: $numberCycle")

        when (type) {
            "next" -> numberCycle++
            "before" -> numberCycle--
        }

        filterCycleDataList.value?.forEach {
            Timber.i("contents numberCycle forEach: $numberCycle")
            if (it.number_of_cycle == numberCycle) {
                contentsCycleData.postValue(it)
                Timber.i("contents numberCycle after postValue: $numberCycle")
                Timber.i("contents changeData: $it")
            }
        }
    }

}

