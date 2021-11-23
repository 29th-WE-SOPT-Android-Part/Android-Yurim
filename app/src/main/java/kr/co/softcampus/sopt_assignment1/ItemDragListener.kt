package kr.co.softcampus.sopt_assignment1

import androidx.recyclerview.widget.RecyclerView

interface ItemDragListener {
    fun onStartDrag(viewHolder : RecyclerView.ViewHolder)
}