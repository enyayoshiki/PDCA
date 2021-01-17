package com.example.pdca.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.pdca.R
import com.example.pdca.databinding.ActivityMainBinding
import com.example.pdca.fragments.dialogs.AddCycleDialogFragment
import com.example.pdca.adapters.viewpagers.MainActivityViewPagerAdapter
import com.example.pdca.data.CycleData
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.i("onCreate MainActivity")
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val adapter =
            MainActivityViewPagerAdapter(
                supportFragmentManager,
                this
            )

        binding.viewPagerAllCycle.adapter = adapter

        binding.addCycleButton.setOnClickListener {
            AddCycleDialogFragment(
                CycleData(),
                false
            ).show(supportFragmentManager, "")
        }
    }

    override fun onStart() {
        super.onStart()
        Timber.i("onStart MainActivity")
    }

    override fun onResume() {
        super.onResume()
        Timber.i("onResume MainActivity")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("onDestroy MainActivity")
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

