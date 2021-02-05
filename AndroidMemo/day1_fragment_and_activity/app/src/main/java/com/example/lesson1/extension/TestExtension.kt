package com.example.lesson1.extension

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf


/**
 * lesson1
 * TestExtension.kt
 * Description:
 *
 * Created by Lina on 2020-08-05
 */

// vararg 는 무엇인가??????!!! 가변인자!!!
inline fun <reified T: AppCompatActivity> Context.startActivity (
    vararg extras: Pair<String, Any?>,
    intentAction: Intent.() -> Unit = {} // 고차함수 사용.
) {
    startActivity(Intent(this, T::class.java).apply {
        putExtras(bundleOf(*extras))
        intentAction()
    })
}

