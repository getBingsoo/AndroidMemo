import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(
    private val parent: ViewGroup,
    private val id: Int
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(id, parent, false)
) {
    abstract fun bind(item: T)
}