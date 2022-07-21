package com.dai.pandyapk.ui.notes

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ScaleGestureDetector
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.dai.pandyapk.R
import com.dai.pandyapk.databinding.FragmentFullScreenBinding
import com.dai.pandyapk.databinding.FragmentNoteDetailBinding
import com.dai.pandyapk.databinding.FragmentNotesListBinding


class FullScreenFragment : Fragment(R.layout.fragment_full_screen) {

    private lateinit var binding: FragmentFullScreenBinding
    private val args by navArgs<FullScreenFragmentArgs>()
    private var count = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFullScreenBinding.bind(view)
        Glide.with(requireContext())
            .load(args.imgUrl)
            .into(binding.ivPhotoFullscreen)

        binding.ivPhotoFullscreen.setOnClickListener {
            when(count){
                0 -> landscape()
                1 -> portrait()
            }
        }
    }

    private fun landscape() {
        requireActivity().requestedOrientation =
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        Toast.makeText(requireContext(), "Modo Horizontal", Toast.LENGTH_SHORT).show()
        count = 1
    }

    private fun portrait() {
        requireActivity().requestedOrientation =
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        Toast.makeText(requireContext(), "Modo Vertical", Toast.LENGTH_SHORT).show()
        count = 0
    }
}