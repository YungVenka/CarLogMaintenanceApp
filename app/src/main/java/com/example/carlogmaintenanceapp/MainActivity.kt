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
        val adapter = MaintenanceAdapter(
            onItemClick = { log ->
                // Отваряне за редакция
                val intent = android.content.Intent(this, AddEditLogActivity::class.java).apply {
                    putExtra("LOG_ID", log.id)
                    putExtra("LOG_TITLE", log.title)
                    putExtra("LOG_MILEAGE", log.mileage)
                    putExtra("LOG_PRICE", log.price)
                    putExtra("LOG_DESC", log.description)
                    putExtra("LOG_DATE", log.date)
                    putExtra("LOG_IMAGE_URI", log.imageUri)
                }
                startActivity(intent)
            },
            onItemLongClick = { log ->
                // Изтриване
                showDeleteDialog(log)
            }
        )
        
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.allLogs.observe(this) { logs ->
            // Обновяване на списъка
            adapter.submitList(logs)
        }

        val fabAddLog = findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.fabAddLog)
        fabAddLog.setOnClickListener {
            val intent = android.content.Intent(this, AddEditLogActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showDeleteDialog(log: MaintenanceLog) {
        AlertDialog.Builder(this)
            .setTitle(R.string.dialog_delete_title)
            .setMessage(R.string.dialog_delete_message)
            .setNegativeButton(R.string.cancel, null)
            .setPositiveButton(R.string.delete) { _, _ ->
                viewModel.delete(log)
            }
            .show()
    }
}