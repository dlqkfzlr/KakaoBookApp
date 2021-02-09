package m.woong.kakaobookapp.utils

import android.widget.ImageView
import android.widget.ToggleButton
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import m.woong.kakaobookapp.R

@BindingAdapter("imageUrl")
fun ImageView.setUrl(url: String) {
    Glide.with(this)
        .load(url)
        .error(R.drawable.ic_error_outline_24)
        .into(this)
}

/* ToggleButton */
@BindingAdapter("favorite")
fun ToggleButton.setFavorite(isFavorite: Boolean) {
    this.isChecked = isFavorite
}