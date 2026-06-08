package com.example.carlogmaintenanceapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.google.android.material.textfield.TextInputEditText

import java.io.File
import java.io.FileOutputStream

class AddEditLogActivity : AppCompatActivity() {

    private val viewModel: MaintenanceViewModel by viewModels()
    private var selectedImageUri: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_log)

        val etTitle = findViewById<TextInputEditText>(R.id.etTitle)
        val etMileage = findViewById<TextInputEditText>(R.id.etMileage)
        val etPrice = findViewById<TextInputEditText>(R.id.etPrice)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val ivLogImage = findViewById<ImageView>(R.id.ivLogImage)
        val btnAddPhoto = findViewById<Button>(R.id.btnAddPhoto)
        val btnRemovePhoto = findViewById<Button>(R.id.btnRemovePhoto)

        val pickMedia = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                // Копиране на снимката локално
                val internalPath = saveImageToInternalStorage(uri)
                if (internalPath != null) {
                    selectedImageUri = internalPath
                    ivLogImage.visibility = View.VISIBLE
                    btnRemovePhoto.visibility = View.VISIBLE
                    ivLogImage.load(File(internalPath))
                }
            }
        }

        btnAddPhoto.setOnClickListener {
            pickMedia.launch("image/*")
        }

        btnRemovePhoto.setOnClickListener {
            selectedImageUri = null
            ivLogImage.visibility = View.GONE
            btnRemovePhoto.visibility = View.GONE
        }

        val logId = intent.getIntExtra("LOG_ID", -1)
        if (logId != -1) {
            etTitle.setText(intent.getStringExtra("LOG_TITLE"))
            etMileage.setText(intent.getIntExtra("LOG_MILEAGE", 0).toString())
            etPrice.setText(intent.getDoubleExtra("LOG_PRICE", 0.0).toString())
            
            selectedImageUri = intent.getStringExtra("LOG_IMAGE_URI")
            if (!selectedImageUri.isNullOrEmpty()) {
                ivLogImage.visibility = View.VISIBLE
                btnRemovePhoto.visibility = View.VISIBLE
                ivLogImage.load(File(selectedImageUri!!))
            }
        }

        btnSave.setOnClickListener {
            val title = etTitle.text.toString().trim()
            val mileageStr = etMileage.text.toString().trim()
            val priceStr = etPrice.text.toString().trim()

            // Проверка за празни полета
            if (title.isEmpty() || mileageStr.isEmpty() || priceStr.isEmpty()) {
                Toast.makeText(this, R.string.error_empty_fields, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Преобразуване към числа
            val mileage = mileageStr.toIntOrNull()
            val price = priceStr.toDoubleOrNull()

            if (mileage == null || price == null) {
                Toast.makeText(this, R.string.error_invalid_numbers, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Създаване на обект
            val log = MaintenanceLog(
                id = if (logId != -1) logId else 0,
                title = title,
                mileage = mileage,
                price = price,
                description = intent.getStringExtra("LOG_DESC") ?: "",
                date = if (logId != -1) intent.getLongExtra("LOG_DATE", System.currentTimeMillis()) else System.currentTimeMillis(),
                imageUri = selectedImageUri
            )
            // Запис в базата
            viewModel.insert(log)

            Toast.makeText(this, R.string.success_save, Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun saveImageToInternalStorage(uri: android.net.Uri): String? {
        return try {
            val inputStream = contentResolver.openInputStream(uri)
            val fileName = "repair_${System.currentTimeMillis()}.jpg"
            val file = File(filesDir, fileName)
            val outputStream = FileOutputStream(file)
            inputStream?.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }
            file.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}