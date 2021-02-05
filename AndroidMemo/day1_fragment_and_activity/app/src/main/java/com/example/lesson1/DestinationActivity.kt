package com.example.lesson1

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lesson1.extension.startActivity

class DestinationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destination)
        val value1 = intent.extras?.get(VALUE_ONE)
        val value2 = intent.extras?.get(VALUE_TWO)
    }

    // 선언 위치는 주체가 되는 쪽. 값을 받아서 사용하는 쪽. 호출하는 쪽에서보단 받는쪽이 좋다!
    companion object {
        const val VALUE_ONE = "value1"
        const val VALUE_TWO = "value2"

        // 여기에 start 함수를 만들어줌. 어디서 이 액티비티를 호출하든 전달인자 필수요건으로 붙이기 가능.
        fun startActivity(context: Context, value1: String, value2: String) {
            context.startActivity<DestinationActivity>(
                VALUE_ONE to value1,
                VALUE_TWO to value2
            )
        } // 이 안에 내용 적어서 MainActivity에서 DestinationActivity.start(this, "value1", "value2") 이렇게 호출한다.
        // 이렇게 하면 좋은점이. startActivity()할 때 에러가 안나니까 추가한 내용들을 놓칠 수 있는데, 이런식으로 하게되면 빌드 시 에러가 나서 조금 더 안전한 코드를 짤 수 있다!!
    }

}
