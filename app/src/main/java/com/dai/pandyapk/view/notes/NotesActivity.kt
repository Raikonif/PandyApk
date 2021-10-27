package com.dai.pandyapk.view.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dai.pandyapk.databinding.ActivityNotesBinding

class NotesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Lista de Notas"

        binding.floatingBtnTakePhoto.setOnClickListener {
            val goToActivityCamera = Intent(this, CameraActivity::class.java)
            goToActivityCamera.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(goToActivityCamera)
        }
        
    }
}