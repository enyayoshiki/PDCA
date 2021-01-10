package com.example.pdca.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

class ContensActivity : AppCompatActivity() {

    companion object {
        const val CYCLEID = "CYCLEID"
        const val CYCLENUMBER = "CYCLENUMBER"


        fun startContents(activity: Context, cycleId: Int, cycleNumber: Int) {
            val intent = Intent(activity, EditCycleActivity::class.java)
            intent.putExtra(CYCLEID, cycleId)
            intent.putExtra(CYCLENUMBER, cycleNumber)
            activity.startActivity(intent)
        }
    }

}