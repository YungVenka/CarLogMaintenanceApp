package com.example.carlogmaintenanceapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class MaintenanceAdapter(private val onItemLongClick: (MaintenanceLog) -> Unit) : 
    ListAdapter<MaintenanceLog, MaintenanceAdapter.MaintenanceViewHolder>(LogDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaintenanceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_maintenance_log, parent, false)
        return MaintenanceViewHolder(view)
    }

    override fun onBindViewHolder(holder: MaintenanceViewHolder, position: Int) {
        val log = getItem(position)
        holder.bind(log, onItemLongClick)
    }

    class MaintenanceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvLogTitle)
        private val tvPrice: TextView = itemView.findViewById(R.id.tvLogPrice)
        private val tvMileage: TextView = itemView.findViewById(R.id.tvLogMileage)

        fun bind(log: MaintenanceLog, onItemLongClick: (MaintenanceLog) -> Unit) {
            tvTitle.text = log.title
            tvPrice.text = "${log.price} €"
            tvMileage.text = "${log.mileage} км"

            itemView.setOnLongClickListener {
                onItemLongClick(log)
                true
            }
        }
    }

    class LogDiffCallback : DiffUtil.ItemCallback<MaintenanceLog>() {
        override fun areItemsTheSame(oldItem: MaintenanceLog, newItem: MaintenanceLog): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MaintenanceLog, newItem: MaintenanceLog): Boolean {
            return oldItem == newItem
        }
    }
}
