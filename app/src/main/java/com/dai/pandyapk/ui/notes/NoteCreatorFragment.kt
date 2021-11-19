package com.dai.pandyapk.ui.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.dai.pandyapk.R
import com.dai.pandyapk.databinding.FragmentNoteCreatorBinding


class NoteCreatorFragment : Fragment(R.layout.fragment_note_creator) {

    private lateinit var binding: FragmentNoteCreatorBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNoteCreatorBinding.bind(view)
    }
}