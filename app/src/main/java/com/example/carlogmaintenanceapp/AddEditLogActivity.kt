package com.example.carlogmaintenanceapp

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class AddEditLogActivity : AppCompatActivity() {

    private val viewModel: MaintenanceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_log)

        val etTitle = findViewById<TextInputEditText>(R.id.etTitle)
        val etMileage = findViewById<TextInputEditText>(R.id.etMileage)
        val etPrice = findViewById<TextInputEditText>(R.id.etPrice)
        val btnSave = findViewById<Button>(R.id.btnSave)

        btnSave.setOnClickListener {
            val title = etTitle.text.toString().trim()
            val mileageStr = etMileage.text.toString().trim()
            val priceStr = etPrice.text.toString().trim()


            if (title.isEmpty() || mileageStr.isEmpty() || priceStr.isEmpty()) {
                Toast.makeText(this, R.string.error_empty_fields, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val mileage = mileageStr.toInt()
            val price = priceStr.toDouble()


            val newLog = MaintenanceLog(
                title = title,
                mileage = mileage,
                price = price,
                description = "",
                date = System.currentTimeMillis(),
            )


            viewModel.insert(newLog)


            Toast.makeText(this, R.string.success_save, Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}