package com.example.comp1786cw2project2.feature.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.example.comp1786cw2project2.MainActivity
import com.example.com1786_cw2project1.R
import com.tunjid.androidx.navigation.StackNavigator

abstract class BaseFragment<VB: ViewBinding, VM: ViewModel> : Fragment() {

    val stackNavigator: StackNavigator by lazy {
        (requireActivity() as MainActivity).stackNavigator
    }

    abstract val viewModel: VM?
    lateinit var viewBinding: VB

    abstract fun onCreateViewBinding(inflater: LayoutInflater): VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = onCreateViewBinding(inflater)
        return viewBinding.root
    }
}