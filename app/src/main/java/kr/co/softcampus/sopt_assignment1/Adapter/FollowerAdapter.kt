package kr.co.softcampus.sopt_assignment1.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.co.softcampus.sopt_assignment1.Data.FollowerData
import kr.co.softcampus.sopt_assignment1.databinding.FollowerListBinding

class FollowerAdapter :
    RecyclerView.Adapter<FollowerAdapter.FollowerViewHolder>() { //<>안에 Adapter가 데이터를 전달할 ViewHolder 클래스 작성

    var followerList = mutableListOf<FollowerData>()

    /*ViewHolder를 생성하고 ItemLayout의 Binding 객체를 만들어 ViewHolder의 생성자로 넘겨주는 함수*/
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FollowerViewHolder { //ViewHolder 객체 반환
        val binding = FollowerListBinding.inflate(
            LayoutInflater.from(parent.context), //LayoutInflater.from을 통해 LayoutInflater를 생성
            parent, false
        )
        return FollowerViewHolder(binding)
    }

    /*재활용되는 뷰를 호출하여 실행되는 함수, ViewHolder와 position의 데이터를 결합시키는 역할*/
    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        holder.onBind(followerList[position]) //ViewHolder의 onBind함수를 호출해서 데이터를 넘겨줌
    }

    override fun getItemCount(): Int = followerList.size

    /* ViewHolder가 가진 View에 Adapter로부터 전달받은 데이터를 붙여주는 함수, onBindViewHolder 호출 시 실행됨 */
    class FollowerViewHolder(private val binding: FollowerListBinding) :
        RecyclerView.ViewHolder(binding.root) { //RecyclerView.ViewHolder 클래스 상속
        fun onBind(data: FollowerData) {
            binding.tvName.text = data.name
            binding.tvIntro.text = data.introduction
            Glide.with(itemView.context)
                .load(data.url)
                .circleCrop()
                .into(binding.ivPhoto)
        }
    }
}