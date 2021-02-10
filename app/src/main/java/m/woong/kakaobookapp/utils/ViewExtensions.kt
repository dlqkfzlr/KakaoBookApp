package m.woong.kakaobookapp.utils

import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import m.woong.kakaobookapp.R


/* ImageView */
@BindingAdapter("imageUrl")
fun ImageView.setUrl(url: String) {
    Glide.with(this)
        .load(url)
        .error(R.drawable.ic_error_outline_24)
        .into(this)
}


/* TextView*/
@BindingAdapter("htmlText")
fun TextView.setParsedHtmlText(text: String) {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        this.text = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY).toString()
    } else {
        this.text = Html.fromHtml(text).toString()
    }
}

/* ToggleButton */
@BindingAdapter("favorite")
fun ToggleButton.setFavorite(isFavorite: Boolean) {
    this.isChecked = isFavorite
}
