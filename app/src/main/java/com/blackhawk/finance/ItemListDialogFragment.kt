package com.blackhawk.finance

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.provider.CalendarContract.Colors
import android.view.KeyEvent
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getColorStateList
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.blackhawk.finance.databinding.FragmentItemListDialogListDialogBinding
import com.blackhawk.finance.model.Entry
import com.blackhawk.finance.viewModel.EntryViewModel
import com.blackhawk.finance.viewModel.EntryViewModelFactory
import java.util.Date


class ItemListDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentItemListDialogListDialogBinding? = null
    private val binding get() = _binding!!

    private lateinit var errorColor: ColorStateList

    private val viewModel: EntryViewModel by activityViewModels {
        EntryViewModelFactory(
            (activity?.application as FinanceApplication).database.entryDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentItemListDialogListDialogBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        errorColor = getColorStateList(requireContext(), R.color.debitColor)


        binding.entryName.setOnEditorActionListener { _, _, _ ->
            run {
                return@setOnEditorActionListener binding.entryValue.requestFocus()
            }
        }

        binding.entryValue.setOnEditorActionListener { _, _, _ ->
            run {
                save()
                return@setOnEditorActionListener true
            }

        }

        binding.saveBtn.setOnClickListener {
            save()
        }

    }

    private fun save()
    {
        val name = binding.entryName.text.toString()
        val value = binding.entryValue.text.toString()

        if(name.isBlank())
        {
            binding.entryName.backgroundTintList = errorColor
            return
        }

        if(value.isBlank())
        {
            binding.entryValue.backgroundTintList = errorColor
            return
        }


        val entry = Entry(
            0,
            name,
            value.toDouble(),
            Date().time,
            binding.isCredit.isChecked
        )
      viewModel.add(entry)
        findNavController().navigate(R.id.action_itemListDialogFragment_to_mainFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}