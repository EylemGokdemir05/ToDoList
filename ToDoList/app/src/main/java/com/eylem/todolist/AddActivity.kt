package com.eylem.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add.*
import java.sql.Timestamp

class AddActivity : AppCompatActivity() {

    private lateinit var db : FirebaseFirestore
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        db= FirebaseFirestore.getInstance()
        auth=FirebaseAuth.getInstance()
    }

    fun addNote(view: View){
        val note=noteText.text.toString()
        if (note!=null){
            //add note
            val noteMap= hashMapOf<String,Any>()
            noteMap.put("email",auth.currentUser!!.email.toString())
            noteMap.put("note",note)
            noteMap.put("date",com.google.firebase.Timestamp.now())

            db.collection("Notes").add(noteMap).addOnCompleteListener { task ->
                if (task.isComplete && task.isSuccessful){
                    val intent= Intent(applicationContext,ListActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }.addOnFailureListener { exception ->
                Toast.makeText(applicationContext,exception.localizedMessage.toString(),Toast.LENGTH_LONG).show()
            }

        }else{
            Toast.makeText(applicationContext,"Please add a note.",Toast.LENGTH_LONG).show()
        }
    }
}