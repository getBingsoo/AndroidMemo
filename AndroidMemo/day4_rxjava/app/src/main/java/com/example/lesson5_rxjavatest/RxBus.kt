package com.example.lesson5_rxjavatest

import io.reactivex.rxjava3.kotlin.ofType
import io.reactivex.rxjava3.subjects.BehaviorSubject

object RxBus {
    val behaviorSubject: BehaviorSubject<RxBusEvent> = BehaviorSubject.create()

    fun onNext(event: RxBusEvent) {
        behaviorSubject.onNext(event)
    }

    inline fun <reified T : RxBusEvent> subscribe(crossinline onNext: (T) -> Unit) =
        behaviorSubject.ofType<T>().subscribe { onNext(it) }
}

interface RxBusEvent

data class SampleRxBusEvent(val text: String) : RxBusEvent