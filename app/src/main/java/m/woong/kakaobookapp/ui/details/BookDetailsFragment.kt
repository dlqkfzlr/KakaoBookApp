package m.woong.kakaobookapp.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import m.woong.kakaobookapp.R
import m.woong.kakaobookapp.databinding.BookDetailsFragmentBinding
import m.woong.kakaobookapp.ui.MainViewModel
import m.woong.kakaobookapp.ui.books.BooksFragment
import m.woong.kakaobookapp.utils.setUrl

@AndroidEntryPoint
class BookDetailsFragment: Fragment(R.layout.book_details_fragment), FavoriteCallBack {

    companion object {
        fun newInstance() = BookDetailsFragment()
    }

    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: BookDetailsFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = BookDetailsFragmentBinding.bind(view)
        with(binding) {
            bookContents.text = "bookContents"
            bookDatetime.text = "bookDatetime"
            bookPrice.text = "bookPrice"
            bookPublisher.text = "bookPublisher"
            bookTitle.text = "bookTitle"
            ivBookCover.setUrl("https://miro.medium.com/max/725/1*xYXHOHl5bmO8peICcGbA_w.png")
        }

    }

    override fun checkFavorite(isbn: String) {
//        viewModel.updateFavorite
    }
}