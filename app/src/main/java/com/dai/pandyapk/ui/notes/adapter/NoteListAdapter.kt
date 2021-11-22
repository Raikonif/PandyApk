package com.dai.pandyapk.ui.notes.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dai.pandyapk.core.BaseViewHolder
import com.dai.pandyapk.databinding.ItemNoteBinding
import com.dai.pandyapk.data.model.Note

class NoteListAdapter(
    private val noteList: List<Note>,
    private val itemClickListener: OnNoteClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnNoteClickListener {
        fun onNoteClick(note: Note)
//        fun onImageNoteClick(image: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = ItemNoteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        val holder = NoteListViewHolder(itemBinding, parent.context)
// click de cada elemento
        itemBinding.root.setOnClickListener {
            val position = holder.bindingAdapterPosition.takeIf {
                    it != DiffUtil.DiffResult.NO_POSITION
                } ?: return@setOnClickListener
            itemClickListener.onNoteClick(noteList[position])
//            itemClickListener.onImageNoteClick(noteList[position].imgUrl!!)
        }
        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is NoteListViewHolder -> holder.bind(noteList[position])
        }

    }


    override fun getItemCount(): Int = noteList.size

    private inner class NoteListViewHolder(
        val binding: ItemNoteBinding,
        val context: Context,
    ) : BaseViewHolder<Note>(binding.root) {
        override fun bind(item: Note) {
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
}