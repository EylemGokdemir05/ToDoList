package com.eylem.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(private val noteArray: ArrayList<String>) : RecyclerView.Adapter<NoteAdapter.NoteHolder> () {

    class NoteHolder(view: View) : RecyclerView.ViewHolder(view){
        var rowText:TextView? =null

        init {
            rowText=view.findViewById(R.id.rowText)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val inflater=LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.note_row,parent,false)
        return NoteHolder(view)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.rowText?.text=noteArray[position]
    }

    override fun getItemCount(): Int {
        return noteArray.size
    }
}