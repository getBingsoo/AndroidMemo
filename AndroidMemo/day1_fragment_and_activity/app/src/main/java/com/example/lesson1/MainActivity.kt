package com.example.lesson1

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.lesson1.extension.startActivity
import kotlinx.android.parcel.Parcelize

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DestinationActivity.startActivity(applicationContext, "value1", "value2")

        Intent(applicationContext, DestinationActivity::class.java).apply {
            putExtras(bundleOf(
                DestinationActivity.VALUE_ONE to "value1",
                DestinationActivity.VALUE_TWO to "value2"
            ))
        }.run {
            startActivity(this)
        }

        // 2. Activity에서 Fragment에 값 전달하기.
        val fragment = DestinationFragment()
        fragment.arguments = bundleOf(Pair(DestinationFragment.VALUE_ONE, "value1"), Pair(DestinationFragment.VALUE_TWO, "value2"))
        supportFragmentManager.beginTransaction().replace(R.id.fl_fragment, fragment).commit()

        // 장원선임님
        var fragmentJw: DestinationFragment? =
            supportFragmentManager.findFragmentById(R.id.fl_fragment) as? DestinationFragment

        DestinationFragment.findFragment(supportFragmentManager, R.id.fl_fragment)
        if (fragmentJw == null) {
            DestinationFragment.newInstance("value1", "value2")
        }

        // 이것도 확장함수를 사용하여 짧게 줄일 수 있다.

////////////////////////////

        // 프로퍼티 - 이니셜블럭 - 메소드 - 클래스 - 인터페이스 - 컴패니언 오브젝트 순서임!! - 포스팅 @@
        // 카카오톡으로 순서 링크 공유주신댔음..
        // 1. MainActivity에서 DestinationActivity로 두개의 스트링 전달하기.
        val intent = Intent(applicationContext, DestinationActivity::class.java)
        intent.putExtra("value1", "value1")
        intent.putExtra("value2", "value2")
        startActivity(intent)

        // 동원선임님
        // 나와다른점 : apply, run을 사용함 & companion object를 사용함!!
        // :: ???? DestinationActivity의 class를 가져온다?? 이런 의미인듯??
        // apply의 목적: 객체의 초기화
        Intent(applicationContext, DestinationActivity::class.java).apply {
            // 다른 곳의 companion object의 값들 쓸 떄 임포트 하지말자. 가독성이 안좋다.
            putExtra(DestinationActivity.VALUE_ONE, "value1")
            putExtra(DestinationActivity.VALUE_TWO, "value2")
            putExtras(bundleOf(
                DestinationActivity.VALUE_ONE to "value1",
                DestinationActivity.VALUE_TWO to "value2"
            ))
        }.run {
            startActivity(this)
        }

        // Kotlin extension function으로 startActivity를 만들어서 사용하면 아래처럼 할 수 있다.
        // extension 파일 만들어서 구현해준다.
        // Serializable보다 Parcelable을 쓰는데..
        // Serializable이 성능이 더 안좋다고 하심. 왜인지는 잘 모르시는듯..

        // 이 함수는 bundleOf()에 값을 실어보낸다.
        // bundleOf()를 까보면 Parcelable을 기본으로 사용한다.
        // KClass - 코틀린 클래스, Class - 자바 클래스
        // Anko라는 라이브러리에서 같은 기능을 제공했는데, 없어져서 extension으로 만든다고 함.
        // 이걸 참고해서 만들었다고 하신다...
        // ************************************************
        startActivity<DestinationActivity>(
            DestinationActivity.VALUE_ONE to "value1",
            DestinationActivity.VALUE_TWO to "value2"
        )

        // ************************************************
    }




    // Parcelable사용 시 @Parcelize 하고 : Parcelable 상속 아래와같이..
    // @Parcelize하면 코드 확 준다..
    // 이거 사용하려면 kotlin-extensions를 참조해야 함
    // ************************************************
//    @Parcelize
//    data class ParcelableClass(val name: String): Parcelable
    // ************************************************
}
