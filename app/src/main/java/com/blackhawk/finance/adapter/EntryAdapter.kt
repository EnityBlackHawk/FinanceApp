package com.blackhawk.finance.adapter

import android.content.Context
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getColorStateList
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.blackhawk.finance.R
import com.blackhawk.finance.model.Entry
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EntryAdapter(
    private val context: Context,
    var onItemClick: ((Int) -> Unit)?=null
) : ListAdapter<Entry, EntryAdapter.EntryViewHolder>(DiffCallback)
{

    companion object{
        private val DiffCallback = object : DiffUtil.ItemCallback<Entry>() {
            override fun areItemsTheSame(oldItem: Entry, newItem: Entry): Boolean
                    = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Entry, newItem: Entry): Boolean
                    = oldItem == newItem

        }
    }

    class EntryViewHolder(view: View?) : RecyclerView.ViewHolder(view!!)
    {

        val entryTag : TextView
        val entryDate: TextView
        val entryValue: TextView


        init {
            if(view == null)
                throw RuntimeException("View was null")
            entryTag   = view.findViewById(R.id.entryName)
            entryDate  = view.findViewById(R.id.entryDate)
            entryValue = view.findViewById(R.id.entryValue)
        }

        fun bind(entry: Entry, context: Context)
        {
            entryTag.text = entry.tag
            entryDate.text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(entry.date))
            entryValue.text = context.resources?.getString(R.string.balance, entry.value)
            if(entry.isCredit)
                entryValue.setTextColor(getColorStateList(context, R.color.creditColor))
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryViewHolder {
        val adapter = LayoutInflater.from(context)
            .inflate(R.layout.entry_item_layout, parent, false)
        return EntryViewHolder(adapter)
    }

    override fun onBindViewHolder(holder: EntryViewHolder, position: Int) {

        holder.bind(getItem(position), context)
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(position)
        }
    }
}