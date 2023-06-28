package com.blackhawk.finance.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blackhawk.finance.model.Entry
import java.util.Date

class EntryViewModel : ViewModel() {

    private var _entryList : MutableLiveData<MutableList<Entry>> = MutableLiveData(mutableListOf())
    val entryList get(): LiveData<MutableList<Entry>> = _entryList

    private var _balance : MutableLiveData<Double> = MutableLiveData(0.0)
    val balance: LiveData<Double> = _balance

    var selectedEntry : MutableLiveData<Entry> = MutableLiveData(null)

    fun add(e: Entry)
    {
        if (_entryList.value?.add(e) == true)
        {
            _balance.value = if(e.isCredit) _balance.value?.plus(e.value)
                                else _balance.value?.minus(e.value)
        }
    }

    fun selectEntry(pos: Int)
    {
        selectedEntry.value = entryList.value?.get(pos)
            ?: throw RuntimeException("Invalid position")
    }

    fun removeSelected()
    {
        val result = _entryList.value?.remove(selectedEntry.value
            ?: throw RuntimeException("No object selected"))

        if(result!!)
        {
            selectedEntry.value?.apply {

                _balance.value = if(isCredit) _balance.value?.minus(value)
                                else _balance.value?.plus(value)
            }

        }

    }

}