package com.dai.pandyapk.view.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dai.pandyapk.databinding.ActivityViewCurrentNoteBinding

class ViewCurrentNoteActivity : AppCompatActivity() {

    private val binding: ActivityViewCurrentNoteBinding by lazy { ActivityViewCurrentNoteBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.floatingBtnEdit.setOnClickListener {

        }
    }
}