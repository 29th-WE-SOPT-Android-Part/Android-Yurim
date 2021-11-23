package kr.co.softcampus.sopt_assignment1

import android.content.Intent
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kr.co.softcampus.sopt_assignment1.databinding.FollowerListBinding

class FollowerAdapter(private val listener: ItemDragListener) :
    RecyclerView.Adapter<FollowerAdapter.FollowerViewHolder>(),
    ItemActionListener { //<>안에 Adapter가 데이터를 전달할 ViewHolder 클래스 작성

    val followerList = mutableListOf<FollowerData>()

    /*ViewHolder를 생성하고 ItemLayout의 Binding 객체를 만들어 ViewHolder의 생성자로 넘겨주는 함수*/
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FollowerViewHolder { //ViewHolder 객체 반환
        val binding = FollowerListBinding.inflate(
            LayoutInflater.from(parent.context), //LayoutInflater.from을 통해 LayoutInflater를 생성
            parent, false
        )
        return FollowerViewHolder(binding, listener)
    }

    /*재활용되는 뷰를 호출하여 실행되는 함수, ViewHolder와 position의 데이터를 결합시키는 역할*/
    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        holder.onBind(followerList[position]) //ViewHolder의 onBind함수를 호출해서 데이터를 넘겨줌

        holder.itemView.setOnClickListener {
            //아이템을 클릭했을 때 실행되는 코드
            val detail_intent = Intent(holder.itemView?.context, DetailActivity::class.java)
            detail_intent.putExtra("img", followerList[position].img)
            detail_intent.putExtra("name", followerList[position].name)
            detail_intent.putExtra("detail", followerList[position].introduction)
            ContextCompat.startActivity(holder.itemView.context, detail_intent, null)
        }
    }

    override fun getItemCount(): Int = followerList.size

    override fun onItemMoved(from: Int, to: Int) {
        if (from == to) {
            return
        }

        val fromItem = followerList.removeAt(from)
        followerList.add(to, fromItem)
        notifyItemMoved(from, to)
    }

    override fun onItemSwiped(position: Int) {
        followerList.removeAt(position)
        notifyItemRemoved(position)
    }

    /* ViewHolder가 가진 View에 Adapter로부터 전달받은 데이터를 붙여주는 함수, onBindViewHolder 호출 시 실행됨 */
    class FollowerViewHolder(private val binding: FollowerListBinding, listener: ItemDragListener) :
        RecyclerView.ViewHolder(binding.root) { //RecyclerView.ViewHolder 클래스 상속

        fun onBind(data: FollowerData) {
            binding.ivPhoto.setImageResource(data.img)
            binding.tvName.text = data.name
            binding.tvIntro.text = data.introduction
        }

        init {
            binding.root.setOnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    listener.onStartDrag(this)
                }
                false
            }
        }
    }
}