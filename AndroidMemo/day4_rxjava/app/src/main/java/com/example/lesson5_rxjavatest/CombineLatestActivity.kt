package com.example.lesson5_rxjavatest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.doOnTextChanged
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.combineLatest
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.activity_combine_latest.*

class CombineLatestActivity : AppCompatActivity() {
    private val compositeDisposable = CompositeDisposable()
    private val nameBehaviorSubject = BehaviorSubject.createDefault("")
    private val ageBehaviorSubject = BehaviorSubject.createDefault("")
    private val addressBehaviorSubject = BehaviorSubject.createDefault("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_combine_latest)

        et_name.doOnTextChanged { text, _, _, _ -> nameBehaviorSubject.onNext(text.toString()) }
        et_age.doOnTextChanged { text, _, _, _ -> ageBehaviorSubject.onNext(text.toString()) }
        et_address.doOnTextChanged { text, _, _, _ -> addressBehaviorSubject.onNext(text.toString()) }

        listOf(nameBehaviorSubject, ageBehaviorSubject, addressBehaviorSubject).combineLatest {
            it.fold(true, {t1, t2 -> t1 && t2.isNotEmpty()})
        }.subscribe {
            btn_send.isEnabled = it
        }.addTo(compositeDisposable)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}
