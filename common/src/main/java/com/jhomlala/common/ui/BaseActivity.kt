package com.jhomlala.common.ui

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel

abstract class BaseActivity<T: ViewModel, V: ViewDataBinding>: AppCompatActivity(){

    protected lateinit var viewModel: T
    protected var viewDataBinding: V? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
    }

    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        this.viewModel = if (!::viewModel.isInitialized) setupViewModel() else viewModel

        viewDataBinding?.lifecycleOwner = this
        viewDataBinding?.setVariable(getBindingVariable(), viewModel)
        viewDataBinding?.executePendingBindings()
    }
    @LayoutRes
    abstract fun getLayoutId(): Int
    abstract fun setupViewModel(): T
    abstract fun getBindingVariable(): Int
}