package com.jhomlala.search

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.jhomlala.search.databinding.SecondFragmentBinding


class SecondFragment : Fragment() {

    companion object {
        fun newInstance() = SecondFragment()
    }

    private lateinit var secondFragmentBinding: SecondFragmentBinding
    private lateinit var viewModel: SecondViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        secondFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.second_fragment, container, false)
        viewModel = ViewModelProviders.of(this).get(SecondViewModel::class.java)


        return secondFragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}
