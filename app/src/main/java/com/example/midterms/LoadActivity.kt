package com.example.midterms

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class LoadActivity : AppCompatActivity() {

    private lateinit var imagePreview: ImageView

    // Modern way to handle getting content (like an image) from another app
    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            // The user has successfully picked an image.
            // Set it as the preview.
            imagePreview.setImageURI(it)

            // In a real app, you might hide the upload button now
            val uploadButton: Button = findViewById(R.id.uploadButton)
            uploadButton.visibility = Button.GONE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.load_activity)

        imagePreview = findViewById(R.id.imagePreview)
        val uploadButton: Button = findViewById(R.id.uploadButton)
        val submitButton: Button = findViewById(R.id.submitButton)

        uploadButton.setOnClickListener {
            // Launch the gallery or file picker to let the user select an image.
            pickImageLauncher.launch("image/*")
        }

        submitButton.setOnClickListener {
            // In a real app, you would upload the image URI to your server.
            // For now, we'll just show a confirmation message and close the screen.
            Toast.makeText(this, "Payment submitted for review!", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}
