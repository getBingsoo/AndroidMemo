package com.example.lesson5_rxjavatest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClickDebounce(view: View) {
        val intent = Intent(this, DebounceActivity::class.java)
        startActivity(intent)
    }

    fun onClickThrottle(view: View) {
        val intent = Intent(this, ThrottleActivity::class.java)
        startActivity(intent)
    }
    fun onClickBuffer(view: View) {
        val intent = Intent(this, BufferActivity::class.java)
        startActivity(intent)
    }
    fun onClickCombineLatest(view: View) {
        val intent = Intent(this, CombineLatestActivity::class.java)
        startActivity(intent)
    }
    fun onClickBehaviorSubject(view: View) {
        val intent = Intent(this, BehaviorSubjectActivity::class.java)
        startActivity(intent)
    }
    fun onClickRxJava(view: View) {
        val intent = Intent(this, RxJavaActivity::class.java)
        startActivity(intent)
    }
}
