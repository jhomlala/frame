package com.jhomlala.common.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment


abstract class BaseFragment<T : BaseViewModel, V : ViewDataBinding> : Fragment() {

    protected lateinit var viewModel: T
    private lateinit var viewDataBinding: V
    var activity: BaseActivity<*, *>? = null


    @LayoutRes
    abstract fun getLayoutId(): Int
    abstract fun setupViewModel(): T


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            this.activity = context
        }
    }

    override fun onDetach() {
        activity = null
        super.onDetach()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.lifecycleOwner = this
        viewModel = setupViewModel()
    }

    fun getViewDataBinding(): V? {
        return if (::viewDataBinding.isInitialized) {
            viewDataBinding
        } else {
            null
        }
    }
}