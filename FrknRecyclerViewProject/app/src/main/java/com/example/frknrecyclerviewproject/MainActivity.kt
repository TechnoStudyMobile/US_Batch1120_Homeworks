package com.example.frknrecyclerviewproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_list_person.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private val people = mutableListOf<Person>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createPeopleList()

        val adapter = PersonListAdapter(people) {
            Toast.makeText(this@MainActivity,
                "Position $it is clicked and I implemented it with lambda!",
                Toast.LENGTH_SHORT).show()

        }
        val layoutManager = LinearLayoutManager(this)

        recyclerView.layoutManager = layoutManager

        //From - interface
//        adapter.onClickListener = object : RecyclerViewClickListener{
//            override fun onClick(position: Int) {
//                Toast.makeText(this@MainActivity, "Position $position is clicked", Toast.LENGTH_SHORT).show()
//            }
//        }

        recyclerView.adapter = adapter


        floatingActionButton.setOnClickListener {
            Toast.makeText(
                this,
                "Someone Click me But I need to learn a LOT! :)) ",
                Toast.LENGTH_LONG
            ).show()
            people.add(Person("LEYLA" + " " + people.size + 1, 30))
            people.add(Person("MECNUN" + " " + people.size + 1, 33))
            (recyclerView.adapter as PersonListAdapter).notifyDataSetChanged()
            recyclerView.smoothScrollToPosition(people.size - 1)
        }

    }


    fun createPeopleList() {
        for (i in 0 until 10) {  // until means <10   .. 10 means include 10
            people.add(Person(name = "Omer Faruk $i", age = Random(80).nextInt()))
            people.add(Person(name = "Hasan $i", age = Random(80).nextInt()))
            people.add(Person(name = "John Lennon $i", age = Random(80).nextInt()))
            people.add(Person(name = "Bluesss $i", age = Random(80).nextInt()))
            people.add(Person(name = "Pink Floyd $i", age = Random(80).nextInt()))
        }

    }

//    private val clickListener: View.OnClickListener = View.OnClickListener { view ->
//        when (view.id){
//            R.id.text_person_name->{
//                Toast.makeText(this, "Person Name Clicked", Toast.LENGTH_LONG).show()
//            }
//            R.id.text_person_age->{
//                Toast.makeText(this, "Person Age Clicked", Toast.LENGTH_LONG).show()
//            }
//        }
//    }

}