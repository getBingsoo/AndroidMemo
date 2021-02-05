package com.example.lesson1


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager

/**
 * A simple [Fragment] subclass.
 */
class DestinationFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_destination, container, false)
        requireActivity() // -> getActivity()와 다른점???
        // requireActivity는 있다는걸 보장해준다. 대신 없으면 Exception. app crash 발생
        // requireParentFragment() -> 이건 조심해서 써야한다. 내가 보장할 수 없다.
        // parentFragmentManager -> 라이브러리 버전이 낮으면 안나온다. 그럼 무슨라이브러리?? Android Support Fragment 1.1.0부터 생겼다. ktx를 포함하면 얘는 기본임.
        activity.let {  } // 이런식으로 쓰는거는 좋은건 아니라고 합니다..!!
        // 왜냐면 크래시를 막기위해 버그를 만드는 느김..?
        // 그러니까 일부러 사전에 방지하려고 require을 쓰시는거임.. 흠...
        // fragment의 onAttach() ~ Detach 사이에는 activity가 존재한다!!

        // supportFragmentManager vs childFragmentManager vs parentFragmentManager
        // childFragmentManager 내(프래그먼트A)가 가지고있는. A프래그에서 B프래그를 붙일 때.
        // 위와 같은 상황에서 fragmentManager을 사용하면, 이건 A의 매니저가 아니라 액티비티의 매니저를 갖다쓰는거기 떄문에 안좋음.
        // 결론적으로 프래그먼트에서 프래그먼트 띄울 때는 childFragmentManager를 사용하자.

        val value1 = arguments?.getString(VALUE_ONE)
        val value2 = arguments?.getString(VALUE_TWO)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    companion object {
        const val VALUE_ONE = "value1"
        const val VALUE_TWO = "value2"

        fun start(context: Context, value1: String, value2: String) {
            val fragment = DestinationFragment()
            fragment.arguments = bundleOf(Pair(DestinationFragment.VALUE_ONE, "value1"), Pair(DestinationFragment.VALUE_TWO, "value2"))
            // pair 대신 A to B 하면 Pair로 생성된다.

//            supportFragmentManager.beginTransaction()
//                .replace(R.id.fl_fragment, fragment)
//                .commit()
            //context.startActivity()
        } // 이 안에 내용 적어서 MainActivity에서 DestinationActivity.start(this, "value1", "value2") 이렇게 호출한다.
        // 이렇게 하면 좋은점이. startActivity()할 때 에러가 안나니까 추가한 내용들을 놓칠 수 있는데, 이런식으로 하게되면 빌드 시 에러가 나서 조금 더 안전한 코드를 짤 수 있다!!

        // JW선임님꺼 더 좋게 수정!!
        fun findFragment(fragmentManager: FragmentManager, @IdRes id: Int): DestinationFragment? {
            return fragmentManager.findFragmentById(id) as? DestinationFragment
        }

        fun newInstance(value1: String, value2: String) = DestinationFragment().apply {
            arguments = bundleOf(VALUE_ONE to value1, VALUE_TWO to value2)
        }
    }

    // fragment 사용 시 주의할점
    // 1. public default 생성자 꼭 그대로 냅두기. 유지!!
    // 2. fragment 재생성.. 객체생성, 데이터전달, 이벤트 핸들링
    // 3. fragment lifecycle vs fragment viewlifecycle -> 뷰만 없어졌을 때 앱이 죽는 이슈가 있을 수 있다.
    // onCreate - onCreateView - onDestoryView - onDestroy 이렇게..
    // onCreateView와 onDestoryView가 여러번 탈 수 있음.


}
