package com.example.scafe

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.input_password
import kotlinx.android.synthetic.main.activity_sign_up.login_btn
import kotlinx.android.synthetic.main.activity_sign_up.login_txt

class SignUpActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("home")

        register()
    }


    private fun register(){
        login_btn.setOnClickListener{

            if (TextUtils.isEmpty(input_Firstname.text.toString())){
                input_Firstname.setError("Please enter first name")
                     return@setOnClickListener
            } else if (TextUtils.isEmpty(input_lastname.text.toString())){
                input_lastname.setError("Please enter last name")
                    return@setOnClickListener
            } else if (TextUtils.isEmpty(input_Id.text.toString())){
                input_Id.setError("Please enter your SLIIT ID number")
                    return@setOnClickListener
            } else if (TextUtils.isEmpty(input_email.text.toString())) {
                input_email.setError("Please enter your Email")
                return@setOnClickListener
            } else if (TextUtils.isEmpty(input_tp.text.toString())){
                input_tp.setError("Please enter Phone Number")
                     return@setOnClickListener
            } else if (TextUtils.isEmpty(input_password.text.toString())){
                input_password.setError("Please enter Passowrd")
                    return@setOnClickListener
            }



            auth.createUserWithEmailAndPassword(input_email.text.toString(),input_password.text.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful){

                      //  val currentUser = auth.currentUser
                     //   val currentUserDb = databaseReference?.child(currentUser?.uid!!)
                     //   currentUserDb?.child("firstname")?.setValue(input_Firstname.text.toString())
                   //     currentUserDb?.child("lastname")?.setValue(input_lastname.text.toString())



                       val user:FirebaseUser? = auth.currentUser
                        user?.sendEmailVerification()
                           ?.addOnCompleteListener {
                                if (it.isSuccessful){
                                    val currentUser = auth.currentUser
                                    val currentUserDb = databaseReference?.child(currentUser?.uid!!)
                                    currentUserDb?.child("firstname")?.setValue(input_Firstname.text.toString())
                                    currentUserDb?.child("lastname")?.setValue(input_lastname.text.toString())

                                    Toast.makeText(this@SignUpActivity,"Registration Success",Toast.LENGTH_LONG).show()
                                    startActivity(Intent(this@SignUpActivity,SignInActivity::class.java))
                                    finish()
                                }
                            }


                    }
                   else{
                       Toast.makeText(this@SignUpActivity,"Registration failed, please try again",Toast.LENGTH_LONG).show()
                    }
                }

        }


        login_txt.setOnClickListener {
            startActivity(Intent(this@SignUpActivity,SignInActivity::class.java))

        }
    }
}


/*
*
*
*
*
*
*
*
*
*
* */