package com.example.lesson5_rxjavatest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_throttle.*
import java.util.concurrent.TimeUnit

class ThrottleActivity : AppCompatActivity() {
    private val compositeDisposable = CompositeDisposable()
    private var num: Int = 0
    private val publishSubject = PublishSubject.create<Unit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_throttle)

        // 클릭 중복 방지용으로 많이 쓰인다.
//        publishSubject.throttleFirst(2000L, TimeUnit.MILLISECONDS)
//            .subscribe {
//                num++
//                tv_output.text = num.toString()
//            }.addTo(compositeDisposable)

        // rxBinding으로 바꾸면..
        btn_increase.clicks()
            .throttleFirst(2000L, TimeUnit.MILLISECONDS)
            .subscribe {
                num++
                tv_output.text = num.toString()
            }.addTo(compositeDisposable)
    }

    fun increase(v: View) {
        publishSubject.onNext(Unit)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
    // 버튼이 눌렸다고 이벤트를 Unit으로 전달
    // 2초동안은 아무리 눌러도 증가하지 않게끔 한다.

}
