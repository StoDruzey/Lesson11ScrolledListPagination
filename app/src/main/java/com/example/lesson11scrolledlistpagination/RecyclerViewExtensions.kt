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
    linearLayoutManager: LinearLayoutManager,
    itemsToLoad: Int, //how many items before the end of the list we start downloading the next page
    onLoadMore: () -> Unit //lambda is a signal to load the next page of items
) {
    addOnScrollListener(object : RecyclerView.OnScrollListener(){
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val lastVisiblePosition = linearLayoutManager.findLastVisibleItemPosition() //the last visible position
            val itemCount = linearLayoutManager.itemCount //how many items are in whole list
            if (itemsToLoad + lastVisiblePosition >= itemCount) {
                onLoadMore()
            }
        }
    })
}