package com.example.lesson5_rxjavatest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.subjects.BehaviorSubject

class BufferActivity : AppCompatActivity() {
    private val compositeDisposable = CompositeDisposable()
    private val behaviorSubject = BehaviorSubject.createDefault(0L) // 아무 의미 없음 1970년 1월 1일
    // 위 시간부터 지금까지의 시간 차이가 2초가 넘으므로 토스트가 뜬다.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buffer)

        behaviorSubject.buffer(2, 1) // 앨범에 있음.. 12, 23, 34 ...
            .map { it[0] to it[1] } // list to pair. 2개를 골라서 pair을 만든다는 뜻.
            .subscribe {
                if (it.second - it.first < 2000L) {
                    super.onBackPressed()
                } else {
                    Toast.makeText(this, "앱을 종료하려면 한번 더 눌러주세요.", Toast.LENGTH_SHORT).show()
                }
            }.addTo(compositeDisposable)

        // 뒤로가기 두번눌러서 앱 종료하기
        // 지금 입력한게 이전에 입력한거보다 2초 이내면 뒤로가기, 아니면 종료 토스트
    }

    override fun onBackPressed() {
        behaviorSubject.onNext(System.currentTimeMillis())
    }
}
