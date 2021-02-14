package m.woong.kakaobookapp.ui.search

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import m.woong.kakaobookapp.R
import m.woong.kakaobookapp.databinding.SearchBookFragmentBinding
import m.woong.kakaobookapp.ui.MainViewModel
import m.woong.kakaobookapp.ui.model.Book
import m.woong.kakaobookapp.utils.showSnackbar

@AndroidEntryPoint
class SearchBookFragment : Fragment(), SelectCallBack {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: SearchBookFragmentBinding
    private lateinit var adapterBook: SearchBookPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.search_book_fragment, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSpinner()
        initEditTextListener()
        initPagingAdapter()
    }

    override fun selectBook(book: Book) {
        val bundle = Bundle().apply {
            putParcelable("BOOK_ITEM", book)
        }
        Navigation.findNavController(binding.root)
            .navigate(R.id.action_books_to_details, bundle)
    }

    private fun initPagingAdapter() {
        adapterBook = SearchBookPagingAdapter(this)
        binding.rvSearch.adapter = adapterBook.withLoadStateFooter(
            footer = BookLoadStateAdapter(adapterBook::retry)
        )
        adapterBook.addLoadStateListener { loadState ->
            with(binding) {
                // refresh시 loadState
                rvSearch.isVisible = loadState.refresh is LoadState.NotLoading
                pbSearchLoading.isVisible = loadState.refresh is LoadState.Loading
                tvNetworkFailure.isVisible = loadState.refresh is LoadState.Error
                ivSearchRetry.isVisible = loadState.refresh is LoadState.Error
            }
            /*val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                // 추가 로딩시 errorState
                binding.rootSearch.showSnackbar("${it.error}")
            }*/
        }

        viewModel.bookList.observe(viewLifecycleOwner,
            Observer { pagingData ->
                hideKeyboard(requireActivity())
                viewLifecycleOwner.lifecycleScope.launch {
                    adapterBook.submitData(pagingData)
                }
            })

        viewLifecycleOwner.lifecycleScope.launch {
            adapterBook.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect { binding.rvSearch.scrollToPosition(0) }
        }
        binding.ivSearchRetry.setOnClickListener { adapterBook.retry() }
    }

    private fun initSpinner() {
        val item = resources.getStringArray(R.array.search_book_target_type)
        val arrAdapter =
            ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_dropdown_item, item)
        with(binding.searchSpinner) {
            adapter = arrAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.selectTargetType(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    viewModel.selectTargetType(0)
                }
            }
            this.setSelection(0)
        }
    }

    private fun initEditTextListener() {
        binding.etSearch.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH    // soft keyboard
                || (event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)    // hard keyboard
            ) {
                search()
                true
            } else {
                false
            }
        }

    }

    fun search() {
        viewModel.searchBooks()
    }

    private fun hideKeyboard(activity: Activity) {
        if (activity.currentFocus != null) {
            val inputMethodManager =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
        }
    }

    companion object {
        val TAG = SearchBookFragment::class.java.simpleName
    }
}