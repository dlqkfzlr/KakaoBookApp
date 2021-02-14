package m.woong.kakaobookapp.ui.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import m.woong.kakaobookapp.R
import m.woong.kakaobookapp.databinding.BookDetailsFragmentBinding
import m.woong.kakaobookapp.ui.MainViewModel
import m.woong.kakaobookapp.ui.model.Book
import m.woong.kakaobookapp.utils.*

@AndroidEntryPoint
class BookDetailsFragment: Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: BookDetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.book_details_fragment, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { args ->
            val book: Book? = args.getParcelable("BOOK_ITEM")
            binding.book = book
            book?.let { setViewContent(it) }
        }
        viewModel.dbUpdateSuccess.observe(viewLifecycleOwner,
        Observer {
            it.getContentIfNotHandled()?.let { isSuccess ->
                val isFavorite = binding.tbBookFavorite.isChecked
                val msg = resources.getString(
                    if (isSuccess && isFavorite)R.string.book_is_favorite
                    else if (isSuccess && !isFavorite) R.string.book_is_not_favorite
                    else R.string.failure_db_update)
                binding.rootDetail.showSnackbar(msg)
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun setViewContent(book: Book){
        with(binding) {
            ivBookCover.setUrl(book.thumbnail)
            bookTitle.text = "책 이름: ${book.title}"
            bookDatetime.text = "출시일: ${book.datetime.toDate()}"
            bookPrice.text = "책 가격: ${book.price.toString().toKoreanWon()}"
            bookPublisher.text = "출판사: ${book.publisher}"
            bookContents.setParsedHtmlText(book.contents)
            tbBookFavorite.isChecked = book.isFavorite
        }
    }

}