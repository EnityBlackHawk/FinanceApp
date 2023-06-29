package com.blackhawk.finance.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.blackhawk.finance.database.EntryDao
import com.blackhawk.finance.model.Entry
import kotlinx.coroutines.DEBUG_PROPERTY_VALUE_OFF
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class EntryViewModel(private val entryDao: EntryDao) : ViewModel() {

    private var _balance : MutableLiveData<Double> = MutableLiveData(0.0)
    var balance: LiveData<Double> = _balance

    var selectedEntry : MutableLiveData<Entry> = MutableLiveData(null)

    private var localEntries : List<Entry>? = null

    init {
        runBlocking {
            _balance.value = setBalance() ?: throw RuntimeException("Erro aqui")
        }
    }

    fun getAll(): Flow<List<Entry>>
    {
        val x = entryDao.getAll()
        viewModelScope.launch {
            localEntries = x.first()
        }
        return x
    }

    suspend fun setBalance() : Double
    {
        var sumValue = 0.0;
        for (e in getAll().first())
        {
            e.apply {
                sumValue = if(isCredit) sumValue + value
                else sumValue - value
            }
        }


       return sumValue
    }

    fun add(e: Entry)
    {
        viewModelScope.launch {
            entryDao.insert(e)
            _balance.value = if (e.isCredit) _balance.value?.plus(e.value)
            else _balance.value?.minus(e.value)

        }
    }

    fun selectEntry(pos: Int)
    {
        selectedEntry.value = localEntries?.get(pos)
    }

    fun removeSelected()
    {
        viewModelScope.launch {
            try{
                entryDao.delete(selectedEntry.value!!)
            }catch (e: Exception)
            {
                Log.e("ViewModel", e.message!!)
            }

            selectedEntry.value?.apply {

                _balance.value = if(isCredit) _balance.value?.minus(value)
                else _balance.value?.plus(value)
            }

        }


    }

}

class EntryViewModelFactory(private val entryDao: EntryDao): ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(EntryViewModel::class.java))
        {
            return EntryViewModel(entryDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}