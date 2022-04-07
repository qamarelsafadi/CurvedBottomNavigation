package com.qamar.curvedbottomnavigation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qamar.curvedbottomnavigation.R
import com.qamar.curvedbottomnavigation.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding  = FragmentHomeBinding.inflate(layoutInflater)
        with(binding){
            val name = arguments?.getString("name","")
            nameItem = name
            return root
        }
    }

}