package kr.co.softcampus.sopt_assignment1

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("imgUrl")
    fun setImage (imageview: ImageView, url: String){
        Glide.with(imageview.context)
            .load(url)
            .circleCrop()
            .into(imageview)
    }
}