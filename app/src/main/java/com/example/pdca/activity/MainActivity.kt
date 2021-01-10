package com.example.pdca.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.pdca.R
import com.example.pdca.databinding.ActivityMainBinding
import com.example.pdca.fragment.AddCycleDialogFragment
import com.example.pdca.fragment.TabAdapter_MainActivity
import com.example.pdca.roomdata.CycleData

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val adapter = TabAdapter_MainActivity(supportFragmentManager, this)

        binding.viewPagerAllCycle.adapter = adapter

        binding.addCycleButton.setOnClickListener {
            val manager = supportFragmentManager
            AddCycleDialogFragment(CycleData()).show(manager, "")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }

    companion object {

        fun startManiActivity(activity: Context){
            val intent = Intent(activity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            activity.startActivity(intent)
        }
    }
}

