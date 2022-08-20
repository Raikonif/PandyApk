package com.dai.pandyapk.ui.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.dai.pandyapk.R
import com.dai.pandyapk.databinding.FragmentNoteEditBinding

class NoteEditFragment : Fragment(R.layout.fragment_note_edit) {
    private lateinit var binding: FragmentNoteEditBinding
    private val args by navArgs<NoteEditFragmentArgs>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNoteEditBinding.bind(view)

        binding.etTitleDetail.hint= args.title
        binding.etDescriptionDetail.hint= args.description
        binding.etTitleDetail.setText(args.title)
        binding.tvDatetimeDetail.text= args.createdAt
        Glide.with(requireContext()).load(args.imgUrl).centerCrop().into(binding.imgPhotoEdit)
        binding.btnSaveNote.setOnClickListener {
            //save note
        }
        binding.btnCancelNote.setOnClickListener {
            //cancel note
            val action = NoteEditFragmentDirections.actionNoteEditFragmentToNotesListFragment()
            findNavController().navigate(action)
        }
        binding.imgPhotoEdit.setOnClickListener {
            //update photo
        }
    }
}