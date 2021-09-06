package com.example.scafe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.core.SnapshotHolder
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_sign_up.*


class HomeActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("home")


        loadProfile()
    }


    private fun loadProfile(){

        val user = auth.currentUser
        val userreference = databaseReference?.child(user?.uid!!)

        textView_email.text = "Email -->" +user?.email

        userreference?.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                textView_firstnameText.text = "Firstname -->" +snapshot.child("firstname").value.toString()
                textView_LastnameText.text ="Lastname -->" +snapshot.child("lastname").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


        logout_btn.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this@HomeActivity,StartupActivity::class.java))
                finish()
        }
    }
}