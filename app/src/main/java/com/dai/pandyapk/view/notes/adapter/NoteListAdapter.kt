package com.dai.pandyapk.view.notes.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dai.pandyapk.R
import com.dai.pandyapk.databinding.ItemNoteBinding
import com.dai.pandyapk.model.Note
import com.dai.pandyapk.view.notes.adapter.internote.OnClickListener

class NoteListAdapter(private val listener: OnClickListener):
    ListAdapter<Note, RecyclerView.ViewHolder>(NoteDiffCallback()) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_note, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val note = getItem(position)

        with (holder as ViewHolder){
            binding.tvNoteTitle.text = note.title
            binding.tvNoteDesc.text = note.description
            binding.tvDatetime.text = note.createdAt.time.toString()
            Glide.with(context)
                .load(note.imgUrl)
                .into(binding.imgPhoto)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = ItemNoteBinding.bind(view)


    }
    class NoteDiffCallback: DiffUtil.ItemCallback<Note>(){
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean = oldItem == newItem
    }



}