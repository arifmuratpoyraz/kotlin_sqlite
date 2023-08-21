package com.example.sqlitedemo.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlitedemo.R
import com.example.sqlitedemo.data.Person

class PersonAdapter(
    private val peopleList: List<Person>,
    private val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {

    interface ItemClickListener {
        fun onMenuButtonClick(view: View, person: Person)
    }

    class PersonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idTextView: TextView = itemView.findViewById(R.id.idTextView)
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val countryTextView: TextView = itemView.findViewById(R.id.countryTextView)
        val totalTextView: TextView = itemView.findViewById(R.id.totalTextView)
        val menuButton: ImageView = itemView.findViewById(R.id.menuButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_person, parent, false)
        return PersonViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val person = peopleList[position]
        holder.idTextView.text = person.id.toString()
        holder.nameTextView.text = person.name
        holder.countryTextView.text = person.country
        holder.totalTextView.text = person.total
        holder.menuButton.setOnClickListener {
            itemClickListener.onMenuButtonClick(holder.menuButton, person)
        }
    }

    override fun getItemCount(): Int {
        return peopleList.size
    }
}