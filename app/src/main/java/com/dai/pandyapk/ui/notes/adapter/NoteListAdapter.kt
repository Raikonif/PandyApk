package com.dai.pandyapk.ui.notes.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dai.pandyapk.core.BaseViewHolder
import com.dai.pandyapk.databinding.ItemNoteBinding
import com.dai.pandyapk.data.model.Note

class NoteListAdapter(private val noteList: List<Note>):
    RecyclerView.Adapter<BaseViewHolder<*>>(){

//    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBiding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteListViewHolder(itemBiding, parent.context)
//        context = parent.context
//        val view = LayoutInflater.from(context).inflate(R.layout.item_note, parent, false)

//        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
//        val note = getItem(position)
        when(holder){
         is NoteListViewHolder -> holder.bind(noteList[position])
        }
//        val note: Note = noteList[position]

//        holder.bind(note)
//        val note : Note = noteList[position]


    }


    override fun getItemCount(): Int = noteList.size

    private inner class NoteListViewHolder(
        val binding: ItemNoteBinding,
        val context: Context
    ) : BaseViewHolder<Note>(binding.root) {

//        val binding = ItemNoteBinding.bind(view)

        override fun bind(item: Note) {
            with(itemView) {
                binding.tvNoteTitle.text = item.title
                binding.tvNoteDesc.text = item.description
                binding.tvDatetime.text = item.createdAt.toString()
                binding.imgFavorite //TODO: logica para cambiar si es o no favorito
                Glide.with(context)
                    .load(item.imgUrl)
                    .centerCrop()
                    .into(binding.imgPhoto)
            }


        }


//interface in the same class
//    interface OnClickListener {
//        fun onClick(note: Note)
//    }


    }

}