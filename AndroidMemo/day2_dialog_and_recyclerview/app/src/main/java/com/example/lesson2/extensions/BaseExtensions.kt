import android.content.Context
import android.content.Intent
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

inline fun <reified T : Fragment> FragmentManager.showFragment(
    fragmentManager: FragmentManager,
    vararg extras: Pair<String, Any?>, @IdRes id: Int
) {
    findFragment<DialogFragment>(fragmentManager, id) ?: T::class.java.newInstance().apply {
        this.arguments = bundleOf(*extras)
    }.let {
        fragmentManager.beginTransaction().replace(id, it)
            .commitAllowingStateLoss()
    }
}


inline fun <reified T : Fragment> FragmentManager.findFragment(fragmentManager: FragmentManager, @IdRes id: Int): T? {
    return fragmentManager.findFragmentById(id) as? T
}


inline fun <reified T : AppCompatActivity> Context.startActivity(
    vararg extras: Pair<String, Any?>,
    intentAction: Intent.() -> Unit = {} // 고차함수 사용.
) {
    startActivity(Intent(this, T::class.java).apply {
        putExtras(bundleOf(*extras))
        intentAction()
    })
}
