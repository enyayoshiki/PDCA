package com.example.pdca.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cycledata")
data class CycleData(
        @PrimaryKey(autoGenerate = true)
        var cycleid: Int = 0,
        var plan: String = "",
        var limit: String = "",
        var doing : String = "",
        var check: String = "",
        var action: String = "",
        var number_of_cycle: Int = 0,
        var finishcycle: Boolean = false,
        var baseId: Int = 0
)