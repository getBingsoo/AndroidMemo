package com.example.lesson3

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

// 0미만의 값을 입력할 수 없는 Delegate Property 추가
// 조건문 대신 사용할 수 있다.
class UnsignedInt: ReadWriteProperty<Any?, Int> {
    private var _value = 0
    override fun getValue(thisRef: Any?, property: KProperty<*>): Int {
        return _value
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
        _value = if (value < 0) {
            print("0 미만의 값을 호출할 수 없음.")
            0
        } else {
            value
        }
    }
}

class MinMaxInteger(
    private val min: Int = Int.MIN_VALUE,
    private val max: Int = Int.MAX_VALUE
): ReadWriteProperty<Any?, Int> {
    private var _value = 0
    override fun getValue(thisRef: Any?, property: KProperty<*>): Int {
        return _value
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
        if (value < min) {
            min
        } else if (value > max) {
            max
        } else {
            _value
        }
    }

}

fun main() {
    var number by MinMaxInteger(20, 100)
    number = 0
    number = 120
    number = 60

    val testClassInvoke: TestClassInvoke = TestClassInvoke(1, "나나")
    testClassInvoke(1)


    // sealed Class

}

// class는 기본으로 final이기 때문에 앞에 open 붙여줘야 상속이 가능하다.

// First-class-citizen

// 고차함수로 받은 함수를 실행하려면 함수이름.invoke()해야한다.

// 심지어 클래스도 클래스 자체를 function처럼 사용할 수 있다. 함수를 상속받고 invoke를 오버라이드 해준다. 그리고 이 클래스의 인스턴스를 만들고 파라미터를 넣어주면 invoke가 실행된다.
// 그냥 이런것도 있다는 것만 알아두자.. 정도?
class TestClassInvoke(private val integer: Int, private val string: String): (Int) -> (Int) {
    override fun invoke(p1: Int): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

// 확장함수 Extension Function..
// 이 형식이 원래 가지고 있는 메소드인 것 처럼 사용가능. method(aa)가 아닌 aa.method() 이렇게 호출 가능하다.
// 보통 Extension 패키지를 하나 만들어서 거기에 모아둔다. ex) StringExtension.kt

// Scope Functions -> Standard.kt 에서 원형을 볼 수 있다. 밑에 takeIf, takeUnless 등 ..
// 초기화 - apply, also
// 널체크 등 run, let
// this: apply, run
// it: also, let
// with: 커스텀뷰의 어트리뷰트에서 뭔가 해줘야할 때


// Data Class - 데이터를 저장하기 위한 목적으로 쓰는 클래스
// deep copy를 지원한다. copy()메소드 사용하면 레퍼런스가 아닌 값이 복사가 된다.(깊은 복사)

// sealed Class - enum이랑 비슷하지만 다르다. 비슷한 클래스들끼리 모아놓음.
// when문 사용 시 else 사용하지 않는게 좋다. sealed에 뭔가 추가되더라도 else를 사용해버리면 추가된 분기를 when 추가하지 않아도 에러가 안나서 실수할 수 있음.
sealed class SealedClass {
    class A: SealedClass()
    class B: SealedClass()
    class C: SealedClass()
}

// 이렇게 when의 결과가 return될 때는 sealedClass에 있는게 when문에 없으면 에러가 발생하지만,
// return하지않고 그냥 when문을 사용할 때는 에러가 발생하지 않는다.
fun sum(sealedClass: SealedClass) = when(sealedClass) {
    is SealedClass.A -> TODO()
    is SealedClass.B -> TODO()
    is SealedClass.C -> TODO()
}