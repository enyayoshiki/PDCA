package com.example.pdca.roomdata

import androidx.databinding.ObservableField

data class ObserveEditCycleData(
        var editPlan: ObservableField<String>,
        var editLimit: ObservableField<String>,
        var editDoing : ObservableField<String>,
        var editCheck: ObservableField<String>,
        var editAction: ObservableField<String>
)

