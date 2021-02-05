import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import java.lang.IllegalArgumentException

typealias Observer<T> = (event: T) -> Unit

class EventSource<T> {
    // 옵저버를 저장하는 리스트 -> 고차함수를 저장하는 형태!
    private val observers = mutableListOf<Observer<T>>()

    // 1. 옵저버를 추가하는 함수
    fun addObserver(observer: Observer<T>) {
        observers += observer
    }

    // 2. notify 함수
    fun notify(event: T) {
        observers.forEach { it(event) }
    }
}

fun main() {
    val eventSource = EventSource<String>()
    eventSource.addObserver { println("첫번째 옵저버: $it") }
    eventSource.addObserver { println("두번째 옵저버: $it") }
    eventSource.notify("Hello")
    eventSource.notify("Observer Pattern")

    Observable // io.reactivex.rxjava3.core.Observable 를 임포트해야함!
        .just(0, 1, 2, 3) // 생산
        .map { it * 2 } // 가공
        .subscribe { println(it) } // 소비


    Observable.just(1, 2, 3, 4)
        .subscribe(object : io.reactivex.rxjava3.core.Observer<Int> {
            override fun onComplete() {
                // 배송 완료 (전부 다 보냈을 때)
                println("onComplete")
            }

            override fun onSubscribe(d: Disposable?) {
                // 전달할 준비가 되면 호출
                // d를 통해 취소할 수 있다.
                println("onSubscribe")
            }

            override fun onNext(t: Int?) {
                // 상품의 갯수만큼 호출
                println("onNext: $t")
            }

            override fun onError(e: Throwable?) {
                // 상품 주문 시 잘못되었을 때 문자로 알려줌
                // 에러는 한번만 들어온다. 2에서 발생하면 3이랑 4는 못받는다고 보면 됨.
                println("onError")
            }
        })

    RxJavaPlugins.setErrorHandler {
        println(it.message)
    } // 이거 한 줄 추가해놓으면 global하게 에러 처리 가능.
    // 아래에서 두번째 파라미터에서 에러 처리 해주지 않아도 디폴트로 여기에 정의된 에러처리가 호출된다.
    // 둘다하면 아래꺼가 호출됨. (오버라이드 개념)

    Observable.just(1, 2, 3, 4)
        .map {
            if (it == 3) {
                throw IllegalArgumentException("3번쨰 missing")
            }
        }
        .subscribe({ // subscribe
            // 파라미터 한개만 하면 onNext
        }, {
        }) // 에러 처리하려면 두번째까지
}
