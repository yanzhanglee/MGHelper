package com.example.mghelper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecordHistoryAdapter(val context: Context, val records: ArrayList<Record>):
    RecyclerView.Adapter<RecordHistoryAdapter.ViewHolder>() {
    class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.recorditem_view,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return records.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var curRecord = records.get(position)
        holder.itemView.apply {
//            recordItemDate.text = curRecord.date
        }
    }

}