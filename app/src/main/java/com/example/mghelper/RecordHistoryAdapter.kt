package com.example.mghelper

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.example.mghelper.databinding.RecorditemBinding
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class RecordHistoryAdapter(val context: Context,
                           private val options: FirebaseRecyclerOptions<Record>,
                           private val currentUserName: String?
):
    FirebaseRecyclerAdapter<Record, ViewHolder>(options) {
    class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView)
    companion object {
        const val TAG = "Adapter"
        const val RECORD_TYPE_MOUTH = 1
        const val RECORD_TYPE_EYES = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordItemViewHolder {

        return if (viewType!=null){
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.recorditem, parent, false)
            val binding = RecorditemBinding.bind(view)
            RecordItemViewHolder(binding)
        }else{
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.recorditem, parent, false)
            val binding = RecorditemBinding.bind(view)
            RecordItemViewHolder(binding)
        }
    }

    override fun getItemViewType(position: Int): Int {
        Log.d("debug",options.snapshots[position].recordType.toString())
        return if (options.snapshots[position].recordType != null) RECORD_TYPE_MOUTH else RECORD_TYPE_EYES
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, model: Record) {
        Log.d("debug","222")
        Log.d("debug",model.date.toString())
        (holder as RecordItemViewHolder).bind(model)
    }

    inner class RecordItemViewHolder(private val binding: RecorditemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Record){
            binding.recordItemDate.text = item.date.toString()
            if(item.recordType == RECORD_TYPE_MOUTH){
                binding.recordItemContent.text = "Mouth Data Recorded."
            }else{
                binding.recordItemContent.text = "Eyes Data Recorded."
            }
        }
    }


}