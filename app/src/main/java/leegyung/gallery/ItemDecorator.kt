package leegyung.gallery

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemDecorator(private val mHeight : Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets( outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = mHeight
        outRect.top = mHeight
        outRect.right = mHeight + 5
        outRect.left = mHeight + 5

    }
}