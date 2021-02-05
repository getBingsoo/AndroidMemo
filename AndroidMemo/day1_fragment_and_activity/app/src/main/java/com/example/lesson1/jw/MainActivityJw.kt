package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lesson1.R

class MainActivityJw : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment: DestinationFragment? =
            DestinationFragment.findFragment(supportFragmentManager, R.id.contentFrame)

        if (fragment == null) {
            DestinationFragment.newInstance("value1", "value2").let { fragment ->
                supportFragmentManager.beginTransaction()
                    .add(R.id.contentFrame, fragment)
                    .commit()
            }
        }
    }
}