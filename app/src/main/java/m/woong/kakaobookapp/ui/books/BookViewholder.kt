package m.woong.kakaobookapp.ui.books

import androidx.recyclerview.widget.RecyclerView
import m.woong.kakaobookapp.databinding.BookViewItemBinding
import m.woong.kakaobookapp.ui.details.FavoriteCallBack
import m.woong.kakaobookapp.ui.model.Book

class BookViewholder(private val binding: BookViewItemBinding,
                     private val mCallback: SelectCallBack
) :
    RecyclerView.ViewHolder(binding.root) {
        fun onBind(book: Book){
            with(binding){
                this.book = book
                this.callback = mCallback
                this.executePendingBindings()
            }
        }
}