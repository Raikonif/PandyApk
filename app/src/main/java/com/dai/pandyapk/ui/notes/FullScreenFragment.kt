package com.dai.pandyapk.ui.notes

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ScaleGestureDetector
import android.view.View
import android.view.ViewGroup
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
                if (count == 0) {
                    requireActivity().requestedOrientation =
                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
//                requireActivity().onBackPressed()
                    count++
                } else {
                    requireActivity().requestedOrientation =
                        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                    count = 0
                }
            }
    }
}