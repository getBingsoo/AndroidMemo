package com.example.lesson5_rxjavatest.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.lesson5_rxjavatest.R
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.merge
import kotlinx.android.synthetic.main.activity_naver_movie_search.*
import java.util.concurrent.TimeUnit

class NaverMovieSearchActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()
    private val vm by lazy { NaverMovieSearchViewModel() }
    private val naverMovieSearchAdapter = NaverMovieSearchAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_naver_movie_search)
        initView()
    }

    private fun initView() {
        rv_content.adapter = naverMovieSearchAdapter
    }

    override fun onResume() {
        super.onResume()
        bindViewModel()
    }

    private fun bindViewModel(){
        val textChange = et_input.textChanges()
            .debounce(1500L, TimeUnit.MILLISECONDS) // 유저가 입력 후 1.5초 안에 전송버튼을 누르는걸 방지?
            .map { it.toString() }
        // 두개의 스트림을 한개의 스트림으로 합침(입력 + 클릭)
        // 비어있지 않은것만 필터링해서 넘겨준다.

        val searchClick = btn_search.clicks()
            .map { et_input.text.toString() }
        listOf(textChange, searchClick)
            .merge()
            .filter(String::isNotBlank)
            .throttleFirst(1000L, TimeUnit.MILLISECONDS)
            .subscribe(vm::searchMovie)
            .addTo(compositeDisposable)

        // 값이 변했을 때. 옵저버를 통해서 메인쓰레드에서 변경한다.
        // 에러가 발생했을 떄는 showError 호출
        // 로딩 -> 로딩바 변경작업
        // movieItems -> 어댑터의 resetAll 호출.
        // 뷰모델이 가지고있는 다양한 정보들이 변경될 때 이 subscribe 내부의 함수들이 동작한다.
        vm.errorSubject
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::showError)
            .addTo(compositeDisposable)
        vm.loadingSubject
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { pb_loading.isVisible = it }
            .addTo(compositeDisposable)
        vm.movieItemsSubject
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(naverMovieSearchAdapter::resetAll)
            .addTo(compositeDisposable)
    }

    override fun onPause() {
        unbindViewModel()
        super.onPause()
    }

    private fun unbindViewModel() {
        compositeDisposable.clear()
        vm.unbindViewModel()
    }

    private fun showError(throwable: Throwable) {
        Toast.makeText(this, throwable.message, Toast.LENGTH_SHORT).show()
    }
}
