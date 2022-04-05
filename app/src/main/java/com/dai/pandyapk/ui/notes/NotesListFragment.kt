package com.dai.pandyapk.ui.notes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
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


class NotesListFragment : Fragment(R.layout.fragment_notes_list),
    NoteListAdapter.OnNoteClickListener {

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
            when (result) {
                is Resource.Loading -> {
                    binding.pbNoteList.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    binding.pbNoteList.visibility = View.GONE
                    if (result.data.isEmpty()) {
                        binding.emptyContainer.visibility = View.VISIBLE
                        return@Observer
                    } else {
                        binding.emptyContainer.visibility = View.GONE
                    }
                    binding.rvNotes.addItemDecoration(
                        DividerItemDecoration(
                            context,
                            DividerItemDecoration.VERTICAL
                        )
                    )
                    binding.rvNotes.adapter = NoteListAdapter(result.data, this)

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



    override fun onNoteClick(note: Note) {
        val action = NotesListFragmentDirections.actionNotesListFragmentToNoteDetailFragment(
            note.title!!,
            note.description!!,
            note.imgUrl!!,
            note.createdAt?.toDate().toString(),
            note.favorite!!
        )
        findNavController().navigate(action)

    }

    override fun onImageNoteClick(note: Note, image: String) {
        TODO("Not yet implemented")
    }

    override fun onFavoriteButtonClick(note: Note, favorite: Boolean?) {
        viewModel.registerFavoriteButtonState(note.createdAt?.toDate().toString(), favorite!!).observe(viewLifecycleOwner){ result ->
            when (result) {
                is Resource.Loading -> { }

                is Resource.Success -> { }

                is Resource.Failure -> {
                    binding.pbNoteList.visibility = View.VISIBLE
                    activity?.toast("Ocurrio un Error ${result.exception}")
                }
            }
        }
    }

// TODO: AÑADIR FUNCION DE TOCAR EN LA IMAGEN PARA AGRANDAR
//    override fun onImageNoteClick(image: String) {
//        val action = NotesListFragmentDirections.actionNotesListFragmentToImageNoteDetailFragment()
//        findNavController().navigate(action)
//    }
}
