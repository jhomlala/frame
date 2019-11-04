package com.jhomlala.search

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.jhomlala.search.databinding.TestFragmentBinding


class TestFragment : Fragment() {

    companion object {
        fun newInstance() = TestFragment()
    }

    private lateinit var testFragmentBinding: TestFragmentBinding
    private lateinit var viewModel: TestViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        testFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.test_fragment, container, false)
        viewModel = ViewModelProviders.of(this).get(TestViewModel::class.java)
        testFragmentBinding.viewModel = viewModel
        testFragmentBinding.executePendingBindings()
        return testFragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeToViewModel()
        // TODO: Use the ViewModel
    }

    private fun subscribeToViewModel(){
        Log.d("Test","Subscribe to view model");
        viewModel.onNextClickedEvent.observe(this, Observer {
            Log.d("Test","Next clicked!!");
            findNavController().navigate(R.id.action_test_fragment)
        })
    }

}
