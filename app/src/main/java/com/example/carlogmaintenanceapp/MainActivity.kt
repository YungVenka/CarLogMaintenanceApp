package com.example.carlogmaintenanceapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val viewModel: MaintenanceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = MaintenanceAdapter { log ->
            showDeleteDialog(log)
        }
        
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.allLogs.observe(this) { logs ->
            adapter.submitList(logs)
        }
    }

    private fun showDeleteDialog(log: MaintenanceLog) {
        AlertDialog.Builder(this)
            .setTitle("Изтриване")
            .setMessage("Сигурни ли сте, че искате да изтриете този ремонт?")
            .setNegativeButton("Отказ", null)
            .setPositiveButton("Изтрий") { _, _ ->
                viewModel.delete(log)
            }
            .show()
    }
}