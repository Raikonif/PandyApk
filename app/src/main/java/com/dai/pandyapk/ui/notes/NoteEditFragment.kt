package com.dai.pandyapk.ui.notes

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.dai.pandyapk.R
import com.dai.pandyapk.databinding.FragmentNoteEditBinding

class NoteEditFragment : Fragment(R.layout.fragment_note_edit) {
    private lateinit var binding: FragmentNoteEditBinding
    private val args by navArgs<NoteEditFragmentArgs>()
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_IMAGE_GALLERY = 2
    private var RESULT_LOAD_IMAGE = 0
    private var bitmap: Bitmap? = null
    private var selectedPhotoUri: Uri? = null
    //maybe implement ViewModel?

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNoteEditBinding.bind(view)
        //TODO: get note ID from args
        binding.etTitleDetail.hint = args.title
        binding.etTitleDetail.setText(args.title)
        binding.etDescriptionDetail.hint = args.description
        binding.tvDatetimeDetail.text = args.createdAt

        Glide.with(requireContext()).load(args.imgUrl).centerCrop().into(binding.imgPhotoEdit)
        binding.btnSaveNote.setOnClickListener {
            //save note

        }

        binding.btnCancelNote.setOnClickListener {
            val action = NoteEditFragmentDirections.actionNoteEditFragmentToNotesListFragment()
            findNavController().navigate(action)
        }

        binding.imgPhotoEdit.setOnClickListener {
            selectPhoto()
        }
    }

    private fun selectPhoto() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_IMAGE_GALLERY)
        resultImage(REQUEST_IMAGE_GALLERY)
    }

    fun resultImage(request: Int): Int {
        RESULT_LOAD_IMAGE = request
        return RESULT_LOAD_IMAGE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        /*if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.ivPhotoSelected.setImageBitmap(imageBitmap)
            bitmap = imageBitmap

        } else*/ if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == Activity.RESULT_OK) {
            selectedPhotoUri = data?.data
            bitmap = MediaStore.Images.Media.getBitmap(
                requireActivity().contentResolver,
                selectedPhotoUri
            )
            binding.imgPhotoEdit.setImageBitmap(bitmap)
//            binding.ivPhotoSelected.setImageURI(selectedPhotoUri)
        }
//        changeStateViewVisibility()
    }
}