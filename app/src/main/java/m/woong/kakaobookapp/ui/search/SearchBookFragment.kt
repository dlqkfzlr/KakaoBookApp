package m.woong.kakaobookapp.ui.search

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.paging.PagingData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import m.woong.kakaobookapp.R
import m.woong.kakaobookapp.databinding.SearchBookFragmentBinding
import m.woong.kakaobookapp.ui.MainViewModel
import m.woong.kakaobookapp.ui.model.Book

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
        setSpinner()
        setEditTextIMEListener()
        setPagingAdapter()
    }

    override fun selectBook(book: Book) {
        val bundle = Bundle().apply {
            putParcelable("BOOK_ITEM", book)
        }
        Navigation.findNavController(binding.root)
            .navigate(R.id.action_books_to_details, bundle)
    }

    private fun setPagingAdapter() {
        adapterBook = SearchBookPagingAdapter(this)
        binding.rvSearch.adapter = adapterBook
        viewModel.bookList.observe(viewLifecycleOwner,
            Observer {
                viewLifecycleOwner.lifecycleScope.launch {
                    hideKeyboard(requireActivity())
                    adapterBook.submitData(PagingData.from(it))
                }
            })
    }

    private fun setSpinner() {
        val item = resources.getStringArray(R.array.search_book_target_type)
        val arrAdapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_dropdown_item, item)
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

    private fun setEditTextIMEListener() {
        binding.etSearch.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when(actionId){
                EditorInfo.IME_ACTION_SEARCH -> {
                    viewModel.searchBooks()
                    true
                }
                else -> false
            }
        }

    }

    fun hideKeyboard(activity: Activity) {
        if (activity.currentFocus != null) {
            val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
        }
    }

    companion object {
        fun newInstance() = SearchBookFragment()
        val TAG = SearchBookFragment::class.java.simpleName
    }
}