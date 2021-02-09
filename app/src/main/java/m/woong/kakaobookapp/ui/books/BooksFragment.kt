package m.woong.kakaobookapp.ui.books

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import m.woong.kakaobookapp.R
import m.woong.kakaobookapp.databinding.BooksFragmentBinding
import m.woong.kakaobookapp.ui.MainViewModel
import m.woong.kakaobookapp.ui.model.Book

@AndroidEntryPoint
class BooksFragment : Fragment(), SelectCallBack {

    companion object {
        fun newInstance() = BooksFragment()
    }

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: BooksFragmentBinding
    private lateinit var adapter: BooksPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.books_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = BooksPagingAdapter()
        binding.rvSearch.adapter = adapter
        viewModel.searchBooks("Love")
        viewModel.bookList.observe(viewLifecycleOwner,
            Observer {
                viewLifecycleOwner.lifecycleScope.launch {
                    adapter.submitData(PagingData.from(it))
                }
            })
    }

    override fun selectBook(book: Book) {
        // 네비게이션 처리
    }
}