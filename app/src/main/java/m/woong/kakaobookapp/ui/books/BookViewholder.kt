package m.woong.kakaobookapp.ui.books

import androidx.recyclerview.widget.RecyclerView
import m.woong.kakaobookapp.databinding.BookViewItemBinding
import m.woong.kakaobookapp.ui.model.Book

class BookViewholder(private val binding: BookViewItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
        fun onBind(book: Book){
            with(binding){
                this.book = book
                this.executePendingBindings()
            }
        }
}