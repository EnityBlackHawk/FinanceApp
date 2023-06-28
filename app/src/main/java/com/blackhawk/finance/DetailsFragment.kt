package com.blackhawk.finance

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.blackhawk.finance.databinding.FragmentDetailsBinding
import com.blackhawk.finance.viewModel.EntryViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class DetailsFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel : EntryViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        viewModel.selectedEntry.value?.apply {
            binding.entryName.text = tag
            binding.textValue.text = getString(R.string.balance, value)

            if(!isCredit)
                binding.textValue.setTextColor(
                    AppCompatResources.getColorStateList(
                        requireContext(),
                        R.color.debitColor
                    )
                )
            binding.isCredit.isChecked = isCredit
        }

        binding.deleteBtn.setOnClickListener {
            viewModel.removeSelected()
            findNavController().navigate(R.id.action_detailsFragment_to_mainFragment)
        }

    }


}