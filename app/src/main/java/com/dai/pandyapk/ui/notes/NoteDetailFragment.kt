package com.dai.pandyapk.ui.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.dai.pandyapk.R
import com.dai.pandyapk.databinding.FragmentNoteDetailBinding


class NoteDetailFragment : Fragment(R.layout.fragment_note_detail) {

    private lateinit var binding: FragmentNoteDetailBinding
    private val args by navArgs<NoteDetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNoteDetailBinding.bind(view)
        val touchImage = binding.imgPhotoDetail

        binding.tvTitleDetail.text = args.title
        binding.tvDescriptionDetail.text = args.description
        Glide.with(requireContext()).load(args.imgUrl).centerCrop().into(touchImage)
        binding.tvDatetimeDetail.text = args.createdAt
//        Glide.with(requireContext()).load(args.imgUrl).into(binding.imgPhotoDetail2)
//        TODO: AÑADIR FUNCION EN TOCAR IMAGEN PARA AGRANDARLA
        binding.imgPhotoDetail.setOnClickListener {
            findNavController().navigate(NoteDetailFragmentDirections.actionNoteDetailFragmentToFullScreenFragment(args.imgUrl))
        }

    }
}