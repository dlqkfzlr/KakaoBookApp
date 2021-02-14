package m.woong.kakaobookapp.ui.search

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import m.woong.kakaobookapp.R
import m.woong.kakaobookapp.databinding.BookLoadStateFooterViewItemBinding

class BookLoadStateViewHolder(
    private val binding: BookLoadStateFooterViewItemBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.ivSearchRetry.also {
            it.setOnClickListener { retry.invoke() }
        }
    }

    fun bind(loadState: LoadState) {
        with(binding){
            pbSearchLoading.isVisible = loadState is LoadState.Loading
            tvNetworkFailure.isVisible = loadState !is LoadState.Loading
            ivSearchRetry.isVisible = loadState !is LoadState.Loading
        }
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): BookLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.book_load_state_footer_view_item, parent, false)
            val binding = BookLoadStateFooterViewItemBinding.bind(view)
            return BookLoadStateViewHolder(binding, retry)
        }
    }
}