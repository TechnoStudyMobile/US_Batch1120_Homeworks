package com.example.frknrecyclerviewproject

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_list_person.*
import kotlinx.android.synthetic.main.item_list_person.view.*
import android.widget.Toast.makeText as makeText1

class PersonListAdapter(val people: MutableList<Person>, val whatToDoOnClicks: (Int)->Unit) :
    RecyclerView.Adapter<PersonListAdapter.PersonViewHolder>() {

    // lateinit var recyclerViewClickListener: RecyclerViewClickListener
    // var onClickListener: RecyclerViewClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_person, parent, false)
        return PersonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.textViewName.text = people[position].name
        holder.textViewAge.text = people[position].age.toString()

// kotlin synthetics way to use views it is worked with extention
//        holder.itemView.text_person_name.text = people[position].name
//        holder.itemView.text_person_age.text = people[position].age.toString()

        holder.button.setOnClickListener {
            whatToDoOnClicks(position)
// interface way to do listener
//            onClickListener?.onClick(position)
        }
        Log.d("MyApp", "Binding the ViewHolder on position: " + position)

    }

    override fun getItemCount(): Int {
        return people.size
    }

    class PersonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
      val textViewName = itemView.findViewById<TextView>(R.id.text_person_name)
      val textViewAge = itemView.findViewById<TextView>(R.id.text_person_age)
      val button = itemView.findViewById<Button>(R.id.button)

//        var text_person_name: TextView
//        var text_person_age: TextView
//        var button : Button
//
//        init {
//            text_person_name = itemView.findViewById<TextView>(R.id.text_person_name)
//            text_person_age = itemView.findViewById<TextView>(R.id.text_person_age)
//            button = itemView.findViewById<Button>(R.id.button)
//        }

    }

}

