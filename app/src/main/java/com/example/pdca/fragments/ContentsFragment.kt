package com.example.pdca.fragments

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.pdca.R
import com.example.pdca.databinding.FragmentContentsABinding
import com.example.pdca.databinding.FragmentContentsCBinding
import com.example.pdca.databinding.FragmentContentsDBinding
import com.example.pdca.databinding.FragmentContentsPBinding
import com.example.pdca.viewmodels.ContentsViewModel
import timber.log.Timber

class ContentsPlanFragment : Fragment(R.layout.fragment_contents_p) {

    //Fragment間でもviewModelを維持できるらしい(activityViewModels)
    private val viewModel: ContentsViewModel by activityViewModels()

    private lateinit var binding: FragmentContentsPBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        val id = bundle?.getInt(ID) ?: 0
        val number = bundle?.getInt(NUMBER) ?: 0

        Timber.i("getInt: $id")

        binding = DataBindingUtil.bind(view) ?: return

        binding.apply {
            contentsdata = viewModel
            lifecycleOwner = viewLifecycleOwner

            nextCycleButtonContents.setOnClickListener {
                viewModel.changeCycle("next")
            }
            beforeCycleButtonContents.setOnClickListener {
                viewModel.changeCycle("before")
            }
        }

        viewModel.apply {
            //すでに存在しているデータを表示させる
            setCycleData(id)
        }

    }

    companion object {

        const val ID = "ID"
        const val NUMBER = "NUMBER"

        fun newInstance(id: Int, number: Int): ContentsPlanFragment {
            val fragment = ContentsPlanFragment()
            val bundle = Bundle()
            bundle.apply {
                putInt(ID, id)
                putInt(NUMBER, number)
            }
            fragment.arguments = bundle
            return fragment
        }
    }
}


class ContentsDoFragment : Fragment(R.layout.fragment_contents_d) {

    private val viewModel: ContentsViewModel by activityViewModels()

    private lateinit var binding: FragmentContentsDBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding = DataBindingUtil.bind(view) ?: return


        binding.apply {
            contentsdata = viewModel
            lifecycleOwner = viewLifecycleOwner

            nextCycleButtonContents.setOnClickListener {
                viewModel.changeCycle("next")
            }
            beforeCycleButtonContents.setOnClickListener {
                viewModel.changeCycle("before")
            }
        }
    }

    companion object {


        fun newInstance(): ContentsDoFragment {
            return ContentsDoFragment()
        }
    }
}


class ContentsCheckFragment : Fragment(R.layout.fragment_contents_c) {

    private val viewModel: ContentsViewModel by activityViewModels()

    private lateinit var binding: FragmentContentsCBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = DataBindingUtil.bind(view) ?: return

        binding.apply {
            contentsdata = viewModel
            lifecycleOwner = viewLifecycleOwner

            nextCycleButtonContents.setOnClickListener {
                viewModel.changeCycle("next")
            }
            beforeCycleButtonContents.setOnClickListener {
                viewModel.changeCycle("before")
            }
        }
    }

    companion object {

        fun newInstance(): ContentsCheckFragment {
            return ContentsCheckFragment()
        }
    }
}


class ContentsActionFragment : Fragment(R.layout.fragment_contents_a) {

    //Fragment間でもviewModelを維持できるらしい。
    private val viewModel: ContentsViewModel by activityViewModels()

    private lateinit var binding: FragmentContentsABinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = DataBindingUtil.bind(view) ?: return

        binding.apply {
            contentsdata = viewModel
            lifecycleOwner = viewLifecycleOwner

            nextCycleButtonContents.setOnClickListener {
                viewModel.changeCycle("next")
            }
            beforeCycleButtonContents.setOnClickListener {
                viewModel.changeCycle("before")
            }
        }
    }


    companion object {

        fun newInstance(): ContentsActionFragment {
            return ContentsActionFragment()
        }
    }
}