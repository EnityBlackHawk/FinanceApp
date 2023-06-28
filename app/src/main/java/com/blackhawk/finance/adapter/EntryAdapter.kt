package com.blackhawk.finance.adapter

import android.content.Context
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getColorStateList
import androidx.recyclerview.widget.RecyclerView
import com.blackhawk.finance.R
import com.blackhawk.finance.model.Entry
import java.text.SimpleDateFormat
import java.util.Locale

class EntryAdapter(
    private val context: Context,
    private val data: MutableList<Entry>
) : RecyclerView.Adapter<EntryAdapter.EntryViewHolder>()
{

    var onItemClick: ((Int) -> Unit)?=null

    class EntryViewHolder(view: View?) : RecyclerView.ViewHolder(view!!)
    {

        val entryTag : TextView
        val entryDate: TextView
        val entryValue: TextView

        init {
            if(view == null)
                throw RuntimeException("View was null")
            entryTag = view.findViewById(R.id.entryName)
            entryDate = view.findViewById(R.id.entryDate)
            entryValue = view.findViewById(R.id.entryValue)

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryViewHolder {
        val adapter = LayoutInflater.from(context)
            .inflate(R.layout.entry_item_layout, parent, false)
        return EntryViewHolder(adapter)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: EntryViewHolder, position: Int) {
        val item = data[position]
        holder.apply {
            entryTag.text = item.tag
            entryDate.text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(item.date)
            entryValue.text = context.resources?.getString(R.string.balance, item.value)
            if(item.isCredit)
                entryValue.setTextColor(getColorStateList(context, R.color.creditColor))
        }

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(position)
        }
    }
}