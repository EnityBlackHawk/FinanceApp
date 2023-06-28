package com.blackhawk.finance

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.blackhawk.finance.adapter.EntryAdapter
import com.blackhawk.finance.databinding.FragmentMainBinding
import com.blackhawk.finance.viewModel.EntryViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class MainFragment : Fragment() {


    private lateinit var binding: FragmentMainBinding
    private val viewModel: EntryViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false)
        //binding.recyclerView.adapter = EntryAdapter(requireContext(), DataSource.data)
        binding.viewModel = viewModel

        val adapter = EntryAdapter(requireContext(), viewModel.entryList.value!!)

        adapter.onItemClick = {
            openDetails(it)
        }

        binding.adapterObject = adapter



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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