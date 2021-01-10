package com.example.pdca.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.pdca.R
import com.example.pdca.databinding.FragmentEditABinding
import com.example.pdca.databinding.FragmentEditCBinding
import com.example.pdca.databinding.FragmentEditDBinding
import com.example.pdca.databinding.FragmentEditPBinding
import com.example.pdca.viewmodel.EditCycleViewModel
import com.example.pdca.viewmodel.ViewModelFactory_EditCycle

class EditPlanFragment : Fragment(R.layout.fragment_edit_p) {

    //Fragment間でもviewModelを維持できるらしい(activityViewModels)
    private val viewModel: EditCycleViewModel by activityViewModels {
        ViewModelFactory_EditCycle(
                lifecycleScope,
                requireContext()
        )
    }

    private lateinit var binding :FragmentEditPBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        val id = bundle?.getInt(ID) ?: 0
        val number = bundle?.getInt(NUMBER) ?: 0


        binding = DataBindingUtil.bind(view) ?: return

        //すでに存在しているデータを表示させる
        viewModel.setCycleData(id, number)

        binding.apply {
            editcycledata = viewModel

            saveButtonEdit.setOnClickListener{
                viewModel.updateCycleDate(false)
            }
            imageSaveButttonEdit.setOnClickListener{
                viewModel.updateCycleDate(false)
            }
        }
    }



    companion object {

        const val ID = "ID"
        const val NUMBER = "NUMBER"

        fun newInstance(id: Int, number: Int): EditPlanFragment {
            val fragment = EditPlanFragment()
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






class EditDoFragment : Fragment(R.layout.fragment_edit_d) {

    private val viewModel: EditCycleViewModel by activityViewModels {
        ViewModelFactory_EditCycle(
                lifecycleScope,
                requireContext()
        )
    }

    private lateinit var binding : FragmentEditDBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding = DataBindingUtil.bind(view) ?: return

        binding.apply {
            editcycledata = viewModel

            saveButtonEdit.setOnClickListener{
                viewModel.updateCycleDate(false)
            }
            imageSaveButttonEdit.setOnClickListener{
                viewModel.updateCycleDate(false)
            }
        }
    }

    companion object {


        fun newInstance(): EditDoFragment{
            val fragment = EditDoFragment()
            return fragment
        }
    }
}






class EditCheckFragment : Fragment(R.layout.fragment_edit_c) {

    private val viewModel: EditCycleViewModel by activityViewModels {
        ViewModelFactory_EditCycle(
                lifecycleScope,
                requireContext()
        )
    }

    private lateinit var binding : FragmentEditCBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = DataBindingUtil.bind(view) ?: return

        binding.apply {
            editcycledata = viewModel

            saveButtonEdit.setOnClickListener{
                viewModel.updateCycleDate(false)
            }
            imageSaveButttonEdit.setOnClickListener{
                viewModel.updateCycleDate(false)
            }
        }
    }


    companion object {

        fun newInstance(): EditCheckFragment{
            val fragment = EditCheckFragment()
            return fragment
        }
    }
}





class EditActionFragment : Fragment(R.layout.fragment_edit_a) {

    //Fragment間でもviewModelを維持できるらしい。
    private val viewModel: EditCycleViewModel by activityViewModels {
        ViewModelFactory_EditCycle(
                lifecycleScope,
                requireContext()
        )
    }

    private lateinit var binding : FragmentEditABinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = DataBindingUtil.bind(view) ?: return

        binding.apply {
            editcycledata = viewModel

            saveButtonEdit.setOnClickListener{
                viewModel.updateCycleDate(false)
            }
            imageSaveButttonEdit.setOnClickListener{
                viewModel.updateCycleDate(false)
            }
            nextCycleButtonFragmentEdit.setOnClickListener{
                val manager = activity?.supportFragmentManager ?: return@setOnClickListener
                viewModel.makeNextCycle(manager)
            }
        }
    }


    companion object {

        fun newInstance(): EditActionFragment{
            val fragment = EditActionFragment()
            return fragment
        }
    }
}