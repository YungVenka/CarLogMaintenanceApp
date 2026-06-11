package com.example.carlogmaintenanceapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import coil.load
import java.io.File
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class MaintenanceAdapter(
    private val onItemClick: (MaintenanceLog) -> Unit,
    private val onItemLongClick: (MaintenanceLog) -> Unit,
) : ListAdapter<MaintenanceLog, MaintenanceAdapter.MaintenanceViewHolder>(LogDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaintenanceViewHolder {
        // свързваме XML дизайна на една кутийка със списъка
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_maintenance_log, parent, false)
        return MaintenanceViewHolder(view)
    }

    override fun onBindViewHolder(holder: MaintenanceViewHolder, position: Int) {

        val log = getItem(position)
        holder.bind(log, onItemClick, onItemLongClick)
    }

    class MaintenanceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvLogTitle)
        private val tvPrice: TextView = itemView.findViewById(R.id.tvLogPrice)
        private val tvMileage: TextView = itemView.findViewById(R.id.tvLogMileage)
        private val ivThumbnail: ImageView = itemView.findViewById(R.id.ivLogThumbnail)

        fun bind(
            log: MaintenanceLog,
            onItemClick: (MaintenanceLog) -> Unit,
            onItemLongClick: (MaintenanceLog) -> Unit
        ) {
            tvTitle.text = log.title
            
            // форматираме текста
            tvMileage.text = MaintenanceUtils.formatMileage(log.mileage)
            tvPrice.text = MaintenanceUtils.formatPrice(log.price)

            // зареждане на снимка, ако има
            if (!log.imageUri.isNullOrEmpty()) {
                ivThumbnail.visibility = View.VISIBLE
                ivThumbnail.load(File(log.imageUri))
            } else {
                ivThumbnail.visibility = View.GONE
            }

            // настройка на кликовете
            itemView.setOnClickListener {
                onItemClick(log)
            }

            itemView.setOnLongClickListener {
                onItemLongClick(log)
                true
            }
        }
    }

    // помощен клас за сравняване на списъци
    class LogDiffCallback : DiffUtil.ItemCallback<MaintenanceLog>() {
        override fun areItemsTheSame(oldItem: MaintenanceLog, newItem: MaintenanceLog): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MaintenanceLog, newItem: MaintenanceLog): Boolean {
            return oldItem == newItem
        }
    }
}