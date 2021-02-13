package m.woong.kakaobookapp.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import m.woong.kakaobookapp.R
import m.woong.kakaobookapp.databinding.BookViewItemBinding
import m.woong.kakaobookapp.ui.model.Book

class SearchBookPagingAdapter(
    private val mCallback: SelectCallBack
): PagingDataAdapter<Book, SearchBookViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchBookViewHolder {
        val binding = DataBindingUtil.inflate<BookViewItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.book_view_item,
            parent,
            false
        )
        return SearchBookViewHolder(binding, mCallback)
    }

    override fun onBindViewHolder(holder: SearchBookViewHolder, position: Int) {
        getItem(position)?.let { holder.onBind(it) }
    }

    companion object{
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Book>(){
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean =
                oldItem.isbn == newItem.isbn

            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean =
                oldItem == newItem
        }
    }
}