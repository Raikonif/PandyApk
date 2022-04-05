package com.dai.pandyapk.ui.notes.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dai.pandyapk.R
import com.dai.pandyapk.core.BaseViewHolder
import com.dai.pandyapk.databinding.ItemNoteBinding
import com.dai.pandyapk.data.model.Note
import com.google.firebase.Timestamp
import javax.annotation.meta.When

class NoteListAdapter(
    private val noteList: List<Note>,
    private val itemClickListener: OnNoteClickListener,
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var noteClickListener: OnNoteClickListener? = null

    init {
        noteClickListener = itemClickListener
    }

    interface OnNoteClickListener {
        fun onNoteClick(note: Note)
        fun onImageNoteClick(note: Note, image: String)
        fun onFavoriteButtonClick(note: Note, favorite: Boolean?)
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
            getDateTime(item.createdAt)
            setupImage(item.imgUrl)
//            changeFavoriteState(item) //TODO: logica para cambiar si es o no favorito
        }

        private fun getDateTime(item: Timestamp?) {
            binding.tvDatetime.text = item?.toDate().toString()
        }

        private fun setupImage(item: String?) {
            Glide.with(context)
                .load(item)
                .centerCrop()
                .into(binding.imgPhoto)
        }

        private fun changeFavoriteState(item: Note) {
            binding.imgFavorite.setOnClickListener {
                when (item.favorite) {
                    true -> item.apply { favorite = false }
                    else -> item.apply { favorite = true }
                }
            }

            changeTintFavorite(item)
            noteClickListener?.onFavoriteButtonClick(item, item.favorite)
        }

        private fun changeTintFavorite(item: Note) {
            when (item.favorite) {
                true ->
                    binding.imgFavorite
                        .setImageDrawable(
                            ContextCompat
                                .getDrawable(context, R.drawable.ic_baseline_favorite_24)
                        )
                else ->
                    binding.imgFavorite
                        .setImageDrawable(
                            ContextCompat
                                .getDrawable(context, R.drawable.ic_baseline_favorite_border_24)
                        )
            }
        }
    }
}