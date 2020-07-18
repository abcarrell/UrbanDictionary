package com.acarrell.urbandictionary.ui.main

import android.app.Activity
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.acarrell.urbandictionary.BR
import com.acarrell.urbandictionary.R
import com.acarrell.urbandictionary.adapter.DictionaryAdapter
import com.acarrell.urbandictionary.application.UDApplication
import com.acarrell.urbandictionary.databinding.MainFragmentBinding
import com.acarrell.urbandictionary.events.Event
import com.acarrell.urbandictionary.events.SortEvent
import com.acarrell.urbandictionary.events.ToastEvent
import com.acarrell.urbandictionary.events.UpdateEvent
import com.acarrell.urbandictionary.viewmodel.MainViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.dictionary_entry.*
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }
    private lateinit var applicationState: UDApplication
    private lateinit var viewModel: MainViewModel
    private var compositeDisposable: CompositeDisposable? = null
    private lateinit var viewDataBinding: MainFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        applicationState = (requireActivity().application) as UDApplication
        @Suppress("DEPRECATION")
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.main_fragment, this.main, false, DataBindingUtil.getDefaultComponent())
        viewDataBinding.viewModel = viewModel
        viewDataBinding.entriesList.apply {
            addItemDecoration(DividerItemDecoration(this.context, LinearLayoutManager.VERTICAL))
        }
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.serviceController = applicationState.getServiceController()

    }

    override fun onResume() {
        super.onResume()
        viewDataBinding.entriesList.adapter = viewModel.adapter
        compositeDisposable = CompositeDisposable()
        compositeDisposable!!.add(
            viewModel.eventBus.subscribe {
                when (it) {
                    is ToastEvent -> handleToastEvent(it)
                    is UpdateEvent -> handleBindingSignal()
                    is SortEvent -> handleSortEvent(it)
                }
            }
        )
        viewDataBinding.searchTerm.setOnEditorActionListener { _, _, event ->
            return@setOnEditorActionListener if (event.action == KeyEvent.ACTION_DOWN) {
                viewModel.search()
                true
            } else false
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.destroy()
        compositeDisposable?.apply {
            clear()
            dispose()
        }
    }

    private fun handleToastEvent(event: ToastEvent) {
        Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
    }

    private fun handleBindingSignal() {
        val imm = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
        viewDataBinding.notifyPropertyChanged(BR.view_model)
    }

    private fun handleSortEvent(event: SortEvent) {
        viewDataBinding.sortButton.setImageDrawable(ContextCompat.getDrawable(requireContext(), event.sortKey.iconRes))
    }
}
