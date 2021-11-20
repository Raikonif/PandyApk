package com.dai.pandyapk.ui.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.dai.pandyapk.R
import com.dai.pandyapk.core.Resource
import com.dai.pandyapk.databinding.FragmentNotesListBinding
import com.dai.pandyapk.data.model.Note
import com.dai.pandyapk.data.remote.NoteListDataSource
import com.dai.pandyapk.domain.noteslist.NoteListRepoImpl
import com.dai.pandyapk.extfun.toast
import com.dai.pandyapk.presentation.NoteListViewModel
import com.dai.pandyapk.presentation.NoteListViewModelFactory
import com.dai.pandyapk.ui.notes.adapter.NoteListAdapter
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class NotesListFragment :
    Fragment(R.layout.fragment_notes_list) /*NoteListAdapter.OnClickListener*/ {

    private lateinit var binding: FragmentNotesListBinding
    private val viewModel by viewModels<NoteListViewModel>
    {
        NoteListViewModelFactory(
            NoteListRepoImpl(
                NoteListDataSource()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNotesListBinding.bind(view)

        viewModel.fetchLatestNotes().observe(viewLifecycleOwner, Observer { result ->
            when(result) {
                is Resource.Loading -> {
                    binding.pbNoteList.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    binding.pbNoteList.visibility = View.GONE
                    binding.rvNotes.adapter = NoteListAdapter(result.data)
                }

                is Resource.Failure -> {
                    binding.pbNoteList.visibility = View.VISIBLE
                    activity?.toast("Ocurrio un Error ${result.exception}")
                }
            }
        })

        binding.btnFloatNewNote.setOnClickListener {
            val action = NotesListFragmentDirections.actionNotesListFragmentToCameraFragment()
            findNavController().navigate(action)
        }
    }
}