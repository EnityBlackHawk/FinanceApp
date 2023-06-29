package com.blackhawk.finance

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.blackhawk.finance.adapter.EntryAdapter
import com.blackhawk.finance.databinding.FragmentMainBinding
import com.blackhawk.finance.viewModel.EntryViewModel
import com.blackhawk.finance.viewModel.EntryViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch




class MainFragment : Fragment() {


    private lateinit var binding: FragmentMainBinding
    private val viewModel: EntryViewModel by activityViewModels {
        EntryViewModelFactory(
            (activity?.application as FinanceApplication).database.entryDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = EntryAdapter(requireContext())
        lifecycleScope.launch {
            viewModel.getAll().collect {
                adapter.submitList(it)
            }
        }


        adapter.onItemClick = {
            openDetails(it)
        }

        binding.adapterObject = adapter

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_itemListDialogFragment)
        }

    }

    private fun openDetails(pos: Int)
    {
        viewModel.selectEntry(pos)
        findNavController().navigate(R.id.action_mainFragment_to_detailsFragment)
    }

}