package com.eylem.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_list.*
import java.sql.Timestamp
import java.util.ArrayList

class ListActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private  lateinit var db: FirebaseFirestore

    var noteList : ArrayList<String> = ArrayList()

    var adapter : NoteAdapter?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        auth=FirebaseAuth.getInstance()
        db= FirebaseFirestore.getInstance()
        getData()

        //recyclerview
        var layoutManager=LinearLayoutManager(this)
        recyclerView.layoutManager=layoutManager

        adapter=NoteAdapter(noteList)
        recyclerView.adapter=adapter
    }

    fun addToDo(view: View){
        val intent=Intent(applicationContext,AddActivity::class.java)
        startActivity(intent)
    }

    fun getData(){
        db.collection("Notes").addSnapshotListener{ snapshot, exception ->
            if (exception!=null){
                Toast.makeText(applicationContext,exception.localizedMessage.toString(),Toast.LENGTH_LONG).show()
            }else{
                if (snapshot!=null){
                    if (!snapshot.isEmpty){
                        noteList.clear()
                        val documents=snapshot.documents
                        for (document in documents){
                            val note= document.get("note") as String
                            val email=document.get("email") as String
                            val timeStamp=document.get("date") as com.google.firebase.Timestamp
                            val date=timeStamp.toDate()

                            noteList.add(note)

                            adapter!!.notifyDataSetChanged()
                        }
                    }
                }
            }
        }
    }
}