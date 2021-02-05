package com.example.lesson5_rxjavatest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_sender.*

class SenderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sender)
    }

    fun send(view: View) {
        val text = et_input.text.toString()
        RxBus.onNext(SampleRxBusEvent(text))
    }


}
