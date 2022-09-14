package com.example.lesson11scrolledlistpagination

import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.addVerticalSpace(@DimenRes spaceRes: Int = R.dimen.list_vertical_space) {
    val space = context.resources.getDimensionPixelSize(spaceRes)
    addItemDecoration(object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
//further it is a block for last position in list: last position will deviate from the edge of the screen
//for 32dp - 16dp padding in xml and 16dp in spaceRes
            val position = parent.getChildAdapterPosition(view)
            if (position != parent.adapter?.itemCount?.minus(1)) {
                outRect.bottom = space
            }
        }
    })
}

fun RecyclerView.addPaginationListener(
    linearLayoutManager: LinearLayoutManager
) {
    addOnScrollListener(object : RecyclerView.OnScrollListener(){
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            Log.d("RecyclerView", "onScrolled $dy")
        }
    })
}