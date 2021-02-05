import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T, VH: BaseViewHolder<T>>: RecyclerView.Adapter<VH>(
//    open protected var itemClickListener: ((T, Int) -> Unit)? = null 아래꺼 하다가 잘 안돼서 결국 생성자로 넣었음..
// 만약 더 많은 클릭 리스너를 추가하고 싶다면 onCreateCustomViewHolder에서 viewHolder 구현할 때 ViewHolder().apply 해서 넣으면 된다.
) {

    protected val items = mutableListOf<T>()

    open protected var itemClickListener: ((T, Int) -> Unit)? = null
    open protected var itemLongClickListener: ((T, Int) -> Unit)? = null

    // 아래에 새로운 customViewHolder을 만들었으니까 강제로 이걸 쓰게 해야함.
    // 기본 OnCreateViewHolder을 상속받지 못하게 final
    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return onCreateCustomViewHolder(parent, viewType).apply {
            itemView.setOnClickListener {
                itemClickListener?.invoke(items[adapterPosition], adapterPosition)
            }
            itemView.setOnLongClickListener {
                itemLongClickListener?.invoke(items[adapterPosition], adapterPosition)
                false
            }
        }
    }

    abstract fun onCreateCustomViewHolder(parent: ViewGroup, viewType: Int): VH

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    fun resetAll(newItems: List<T>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun changeItem(position: Int, item: T) {
        items[position] = item
        notifyItemChanged(position)
    }

    fun insertItem(position: Int, item: T) {
//        items[position] = item
        items.add(position, item)
        notifyItemInserted(position)
    }

    fun removeItem(position: Int, item: T) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun clear() {

    }
}