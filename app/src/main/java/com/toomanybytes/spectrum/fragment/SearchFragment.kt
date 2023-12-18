package com.toomanybytes.spectrum.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.toomanybytes.spectrum.R
import com.toomanybytes.spectrum.databinding.FragmentFeedBinding
import com.toomanybytes.spectrum.databinding.FragmentSearchBinding
import com.toomanybytes.spectrum.viewmodel.SearchViewModel

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)


        // 검색어 변경 감지
        binding.searchText.addTextChangedListener {
            viewModel.onTextChanged(it.toString())
        }

        // 검색어 변경으로 인한 백스페이스 아이콘 상태 감지
        viewModel.isVisibility.observe(viewLifecycleOwner, Observer { isEnabled ->
            when(isEnabled){
                true -> binding.searchBackspace.visibility = View.VISIBLE
                false -> binding.searchBackspace.visibility = View.INVISIBLE
            }
        })

        // 백스페이스 클릭시 editText 초기화
        binding.searchBackspace.setOnClickListener {
            binding.searchText.text = null
        }

        return binding.root


    }

}