package com.example.lesson5_rxjavatest.retrofit

import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject


/**
 * lesson5_RxJavaTest
 * NaverMovieSearchViewModel.kt
 * Description:
 *
 * Created by Lina on 2020-09-16
 */

class NaverMovieSearchViewModel {
    private val compositeDisposable = CompositeDisposable()

    val errorSubject = BehaviorSubject.create<Throwable>()
    val loadingSubject = BehaviorSubject.createDefault(false)
    val movieItemsSubject = BehaviorSubject.create<List<NaverMovieResponse.Item>>()

    // 화면이 내려가거나.. 그럴 때 unbind해준다.
    fun unbindViewModel() {
        compositeDisposable.clear()
    }

    fun searchMovie(query: String) {
        NetworkManager.naverApi.getMovies(query)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { loadingSubject.onNext(true) }
            .doAfterTerminate { loadingSubject.onNext(false) }
            .map { it.items }
            .subscribe(movieItemsSubject::onNext, errorSubject::onNext)
            .addTo(compositeDisposable)
    }
}