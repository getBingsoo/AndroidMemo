package com.example.lesson5_rxjavatest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo

class BehaviorSubjectActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_behavior_subject)

        RxBus.subscribe<SampleRxBusEvent> {
            Log.e("log tag", it.text)
        }.addTo(compositeDisposable)
    }

    fun goToSenderActivity(view: View) {
        startActivity(Intent(this, SenderActivity::class.java))
    }
}
