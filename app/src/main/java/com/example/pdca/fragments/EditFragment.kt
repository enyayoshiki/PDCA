package com.example.pdca.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.pdca.R
import com.example.pdca.activities.MainActivity
import com.example.pdca.data.CycleData
import com.example.pdca.databinding.FragmentEditABinding
import com.example.pdca.databinding.FragmentEditCBinding
import com.example.pdca.databinding.FragmentEditDBinding
import com.example.pdca.databinding.FragmentEditPBinding
import com.example.pdca.extention.visible
import com.example.pdca.fragments.dialogs.AddCycleDialogFragment
import com.example.pdca.viewmodels.TestEditCycleViewModel
import timber.log.Timber

class EditPlanFragment : Fragment(R.layout.fragment_edit_p) {

    //Fragment間でもviewModelを維持できるらしい(activityViewModels)
    private val viewModel: TestEditCycleViewModel by activityViewModels()

    private lateinit var binding: FragmentEditPBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val bundle = arguments
        val id = bundle?.getInt(ID) ?: 0
        val number = bundle?.getInt(NUMBER) ?: 0

        binding = DataBindingUtil.bind(view) ?: return

        binding.apply {
            editcycledata = viewModel
            lifecycleOwner = viewLifecycleOwner

            saveButtonEdit.setOnClickListener {
                viewModel.updateCycleDate()
                Toast.makeText(context, R.string.save_text, Toast.LENGTH_SHORT).show()
                MainActivity.startManiActivity(requireActivity())
            }
            nextCycleButtonFragmentEdit.setOnClickListener {
                AddCycleDialogFragment(
                    CycleData(),
                    true
                ).show(childFragmentManager, "")
            }
        }

        viewModel.apply {
            //すでに存在しているデータを表示させる
            setCycleData(id, number)
//            editPlan.observe(viewLifecycleOwner, Observer {
//                isNext.postValue(it.isNotEmpty() && editLimit.value!!.isNotEmpty() && editDoing.value!!.isNotEmpty() && editCheck.value!!.isNotEmpty() && editAction.value!!.isNotEmpty())
//            })
//            editLimit.observe(viewLifecycleOwner, Observer {
//                isNext.postValue(editPlan.value!!.isNotEmpty() && it.isNotEmpty() && editDoing.value!!.isNotEmpty() && editCheck.value!!.isNotEmpty() && editAction.value!!.isNotEmpty())
//            })
            isNext.observe(viewLifecycleOwner, Observer {
                binding.nextCycleButtonFragmentEdit.visible(isNext.value ?: false)
            })
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

    private val viewModel: TestEditCycleViewModel by activityViewModels()

    private lateinit var binding: FragmentEditDBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding = DataBindingUtil.bind(view) ?: return


        binding.apply {
            editcycledata = viewModel
            lifecycleOwner = viewLifecycleOwner

            saveButtonEdit.setOnClickListener {
                viewModel.updateCycleDate()
                Toast.makeText(context, R.string.save_text, Toast.LENGTH_SHORT).show()
                MainActivity.startManiActivity(requireActivity())
            }
            nextCycleButtonFragmentEdit.setOnClickListener {
                AddCycleDialogFragment(
                    viewModel.nextCycle(),
                    true
                ).show(childFragmentManager, "")
            }
        }

        viewModel.apply {
//            editDoing.observe(viewLifecycleOwner, Observer {
//                isNext.postValue(editPlan.value!!.isNotEmpty() && editLimit.value!!.isNotEmpty() && it.isNotEmpty() && editCheck.value!!.isNotEmpty() && editAction.value!!.isNotEmpty())
//            })
            isNext.observe(viewLifecycleOwner, Observer {
                binding.nextCycleButtonFragmentEdit.visible(isNext.value ?: false)
            })
        }
    }

    companion object {


        fun newInstance(): EditDoFragment {
            return EditDoFragment()
        }
    }
}


class EditCheckFragment : Fragment(R.layout.fragment_edit_c) {

    private val viewModel: TestEditCycleViewModel by activityViewModels()

    private lateinit var binding: FragmentEditCBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = DataBindingUtil.bind(view) ?: return

        binding.apply {
            editcycledata = viewModel
            lifecycleOwner = viewLifecycleOwner

            saveButtonEdit.setOnClickListener {
                viewModel.updateCycleDate()
                Toast.makeText(context, R.string.save_text, Toast.LENGTH_SHORT).show()
                MainActivity.startManiActivity(requireActivity())
            }

            nextCycleButtonFragmentEdit.setOnClickListener {
                AddCycleDialogFragment(
                    viewModel.nextCycle(),
                    true
                ).show(childFragmentManager, "")
            }


            viewModel.apply {
//
//                editCheck.observe(viewLifecycleOwner, Observer {
//                    isNext.postValue(editPlan.value!!.isNotEmpty() && editLimit.value!!.isNotEmpty() && editDoing.value!!.isNotEmpty() && it.isNotEmpty() && editAction.value!!.isNotEmpty())
//                })
                isNext.observe(viewLifecycleOwner, Observer {
                    binding.nextCycleButtonFragmentEdit.visible(isNext.value ?: false)
                })
            }
        }
    }

    companion object {

        fun newInstance(): EditCheckFragment {
            return EditCheckFragment()
        }
    }
}


class EditActionFragment : Fragment(R.layout.fragment_edit_a) {

    //Fragment間でもviewModelを維持できるらしい。
    private val viewModel: TestEditCycleViewModel by activityViewModels()

    private lateinit var binding: FragmentEditABinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = DataBindingUtil.bind(view) ?: return

        binding.apply {
            editcycledata = viewModel
            lifecycleOwner = viewLifecycleOwner

            saveButtonEdit.setOnClickListener {
                viewModel.updateCycleDate()
                Toast.makeText(context, R.string.save_text, Toast.LENGTH_SHORT).show()
                MainActivity.startManiActivity(requireActivity())
            }
            nextCycleButtonFragmentEdit.setOnClickListener {
                AddCycleDialogFragment(
                    viewModel.nextCycle(),
                    true
                ).show(childFragmentManager, "")
            }        }




            viewModel.apply {
//                editAction.observe(viewLifecycleOwner, Observer {
//                    isNext.postValue(editPlan.value!!.isNotEmpty() && editLimit.value!!.isNotEmpty() && editDoing.value!!.isNotEmpty() && editCheck.value!!.isNotEmpty() && editAction.value!!.isNotEmpty())
//                })
                isNext.observe(viewLifecycleOwner, Observer {
                    binding.nextCycleButtonFragmentEdit.visible(isNext.value ?: false)
                })
            }
        }


    companion object {

        fun newInstance(): EditActionFragment {
            return EditActionFragment()
        }
    }
}