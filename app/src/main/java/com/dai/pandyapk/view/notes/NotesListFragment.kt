package com.dai.pandyapk.view.notes

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ListAdapter
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dai.pandyapk.R
import com.dai.pandyapk.databinding.ActivityCameraBinding
import com.dai.pandyapk.databinding.FragmentNotesListBinding
import com.dai.pandyapk.model.Note
import com.dai.pandyapk.view.notes.adapter.NoteListAdapter
import com.dai.pandyapk.view.notes.adapter.internote.OnClickListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.NonDisposableHandle.parent


class NotesListFragment : Fragment(R.layout.fragment_notes_list), OnClickListener {

    private lateinit var binding: FragmentNotesListBinding
    private lateinit var listAdapter: NoteListAdapter
    private val mAuth: FirebaseAuth by lazy { Firebase.auth }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNotesListBinding.bind(view)
        setupRecyclerView()
        getAllNotes()
        binding.btnFloatNewNote.setOnClickListener {
            val action = NotesListFragmentDirections.actionNotesListFragmentToNoteCreatorFragment()
            findNavController().navigate(action)
        }
    }

    private fun setupRecyclerView(){
        listAdapter = NoteListAdapter(this)
        binding.rvNotes.apply {
//            ajustar el mismo tamaño siempre
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context!!)
            adapter = listAdapter
        }
    }

    private fun listenNotes(): MutableList<Note>{
        val uid = mAuth.currentUser?.uid
        val db = Firebase.firestore
        var itemNote = Note::class.java
        val dbRef = db.collection("notes")
            .document("$uid")
            .collection("pathology").document()

        dbRef.get().addOnSuccessListener{ document ->
                val note = document.data


            return@addOnSuccessListener
        }


    }

    private fun getAllNotes(){
        val notesData = listenNotes()
        listAdapter.submitList(listenNotes())
    }

    override fun onClick(note: Note) {
        TODO("Not yet implemented")
    }

}