package kr.co.softcampus.sopt_assignment1

interface ItemActionListener {
    fun onItemMoved(from: Int, to: Int)
    fun onItemSwiped(position: Int)
}