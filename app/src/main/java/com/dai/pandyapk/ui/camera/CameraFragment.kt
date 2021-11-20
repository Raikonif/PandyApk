package com.dai.pandyapk.ui.camera

import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dai.pandyapk.R
import com.dai.pandyapk.data.remote.CameraDataResource
import com.dai.pandyapk.data.remote.NoteListDataSource
import com.dai.pandyapk.databinding.FragmentCameraBinding
import com.dai.pandyapk.domain.camera.CameraRepoImpl
import com.dai.pandyapk.domain.noteslist.NoteListRepoImpl
import com.dai.pandyapk.extfun.toast
import com.dai.pandyapk.presentation.CameraViewModel
import com.dai.pandyapk.presentation.CameraViewModelFactory
import com.dai.pandyapk.presentation.NoteListViewModel
import com.dai.pandyapk.presentation.NoteListViewModelFactory
import com.dai.pandyapk.core.Result
import com.google.firebase.Timestamp

class CameraFragment : Fragment(R.layout.fragment_camera) {

    private val REQUEST_IMAGE_CAPTURE = 1
    private lateinit var binding: FragmentCameraBinding
    private var bitmap: Bitmap? = null
    private val viewModel by viewModels<CameraViewModel> {
        CameraViewModelFactory(
            CameraRepoImpl(
                CameraDataResource()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCameraBinding.bind(view)

        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            activity?.toast("no se encontro ninguna app para abrir la camara")
        }

        binding.btnUploadPhoto.setOnClickListener {
            bitmap?.let {
                viewModel.uploadPhoto(
                    it,
                    binding.etPhotoDescription.text.toString().trim(),
                    binding.etTitle.text.toString().trim(),
                    false,
                    Timestamp.now()
                )

                    .observe(viewLifecycleOwner, { result ->
                        when (result) {
                            is Result.Loading -> {
                                Toast.makeText(
                                    requireContext(),
                                    "Uploading Photo",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            is Result.Success -> {
                                findNavController().navigate(R.id.action_cameraFragment_to_notesListFragment)
                            }
                            is Result.Failure -> {
                                Toast.makeText(
                                    requireContext(),
                                    "Error ${result.exception}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    })
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.imgAddPhoto.setImageBitmap(imageBitmap)
            bitmap = imageBitmap
        }
    }
}

