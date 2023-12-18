package com.toomanybytes.spectrum.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.toomanybytes.spectrum.R
import com.toomanybytes.spectrum.databinding.FragmentActionBinding
import com.toomanybytes.spectrum.databinding.FragmentNoticeBinding

class ActionFragment : Fragment() {

    private lateinit var binding: FragmentActionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentActionBinding.inflate(layoutInflater)

        return binding.root

    }

}