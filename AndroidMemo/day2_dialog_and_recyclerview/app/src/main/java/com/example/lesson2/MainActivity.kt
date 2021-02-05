package com.example.lesson2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_view.view.*

class MainActivity : AppCompatActivity() {
    // DialogFragment를 show하기위한 kotlin extension function 만들기.

    private lateinit var adapter: MyAdapter
    private lateinit var manager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list = ArrayList<Int>()
        for(i in 1..100) {
            list.add(i)
        }
        val list2 = (1..100).toList() // kotlin 방식

        adapter = MyAdapter()
        adapter.resetAll(list)
        manager = LinearLayoutManager(this) // 이거 동적으로 추가할 필요 없고, xml에 추가가능
        // app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"

        val recyclerView = findViewById<RecyclerView>(R.id.rv_list)
        recyclerView.apply {
            this.adapter = adapter
            this.layoutManager = manager
        }
    }

    // 생성자로 아무것도 안받는게 좋다!! context는 parent.context에서 가져올 수 있고 리스트는 함수를 통해서 세팅하는 것이 옳다.
    // 어댑터 내부에서 하는게 좋다.
    class MyAdapter : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

        private val items = mutableListOf<Int>() // 이렇게

        fun resetAll(newItems: List<Int>) {
            items.clear()
            items.addAll(newItems)
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            // onClickListener은 여기에 하는 것이 좋다. (매번 안해도 됨)
            // 장석준님: return ViewHolder(parent) 이렇게.
//            return SjViewHolder(parent)
            return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false))
        }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.bind(items[position])
        }

        inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            private val text: TextView = itemView.tv_item

            fun bind(number: Int) {
                text.text = number.toString()

            }
        }
    }

}

// 석준님은 이렇게.
// 아이템뷰와 사실 관련있는건 adapter보다는 이게 관련있으니까 여기에 아이템뷰 생성 관련 코드를 넣는다.
class SjViewHolder(parent:ViewGroup): RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)) {
    private val text: TextView = itemView.tv_item

    fun bind(number: Int) {
        text.text = number.toString()

    }
}