package com.example.pdca

import com.example.pdca.application.PdcaApplication
import com.example.pdca.data.CycleData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CycleDataRepository {


    private val cycleDao = PdcaApplication.db.pdcaCycleDao()

    /**
     * Query all messages.
     */

    suspend fun loadCycleData(): List<CycleData> =
        withContext(Dispatchers.IO) {
            cycleDao.getAllData()
        }

    suspend fun updataCycleData(cycleData: CycleData) {
        withContext(Dispatchers.IO) {
            cycleDao.update(cycleData)

        }
    }

    suspend fun deleteCycleData(cycleData: CycleData) {
        withContext(Dispatchers.IO) {
            cycleDao.delete(cycleData)
        }
    }
}
