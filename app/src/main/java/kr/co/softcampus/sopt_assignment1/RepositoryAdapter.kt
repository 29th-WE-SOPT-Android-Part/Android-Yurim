package kr.co.softcampus.sopt_assignment1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.softcampus.sopt_assignment1.databinding.RepositoryListBinding

class RepositoryAdapter : RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {

    val RepositoryList = mutableListOf<RepositoryData>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RepositoryViewHolder {
        val binding = RepositoryListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RepositoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.onBind(RepositoryList[position])
    }

    override fun getItemCount(): Int = RepositoryList.size

    class RepositoryViewHolder(private val binding: RepositoryListBinding) //뷰홀더는 포장지! 여기서 onBind해준다
        : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: RepositoryData) {
            binding.tvTitle.text = data.title
            binding.tvSubtitle.text = data.subtitle
        }
    }
}