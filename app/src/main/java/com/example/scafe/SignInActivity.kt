package com.example.scafe

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_in.input_password
import kotlinx.android.synthetic.main.activity_sign_in.login_btn
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        auth = FirebaseAuth.getInstance()


        login_btn.setOnClickListener {
            doLogin()
        }

        // forgot password
        forgotpassword_txt.setOnClickListener {

            val builder:AlertDialog.Builder = AlertDialog.Builder(this )
            builder.setTitle("Forgot Password")
            val view:View = layoutInflater.inflate(R.layout.dialog_forgot_password, null)
            val username : EditText = view.findViewById<EditText>(R.id.et_username)
            builder.setView(view)
            builder.setPositiveButton("Reset", DialogInterface.OnClickListener {_ , _ ->

                forgotPassword(username)
            })
            builder.setNegativeButton("Close", DialogInterface.OnClickListener {_ , _ ->  })
            builder.show()
        }

    }
    // forgot password
    private fun forgotPassword(username:EditText){

        if (TextUtils.isEmpty(username.text.toString())){
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(username.text.toString()).matches()){
            return
        }

        auth.sendPasswordResetEmail(username.text.toString())
            .addOnCompleteListener {
                if (it.isSuccessful)
                {
                    Toast.makeText(this@SignInActivity,"Email sent",
                        Toast.LENGTH_LONG).show()
                }
            }

    }

// Login
    private fun doLogin(){


        if (TextUtils.isEmpty(input_Username.text.toString())){
            input_Username.setError("Please enter Email")
            input_Username.requestFocus()
            return
        }
         if (!Patterns.EMAIL_ADDRESS.matcher(input_Username.text.toString()).matches()){
            input_Username.setError("Please enter valied email")
            input_Username.requestFocus()
            return
        }


         if (TextUtils.isEmpty(input_password.text.toString())){
            input_password.setError("Please enter  Password")
            input_password.requestFocus()
            return

        }


        auth.signInWithEmailAndPassword(input_Username.text.toString(),input_password.text.toString())

                .addOnCompleteListener(this) {
                    if (it.isSuccessful){
                        val user:FirebaseUser? = auth.currentUser
                        updateUI(user)
                        //startActivity(Intent(this@SignInActivity,HomeActivity::class.java))
                      //  finish()
                    }
                    else{
                        Toast.makeText(this@SignInActivity,"Sign In failed, please try again",
                                Toast.LENGTH_LONG).show()
                        updateUI(null)
                    }
                }




    }


    public override fun onStart() {
        super.onStart()
        val currentUser:FirebaseUser? = auth.currentUser
        updateUI(currentUser)
    }

    // Verify email address
    private fun updateUI(currentUser: FirebaseUser?){

        if (currentUser != null)
        {
            if (currentUser.isEmailVerified) {
                startActivity(Intent(this@SignInActivity, HomeActivity::class.java))
                finish()
            }
            else{
                Toast.makeText(this@SignInActivity,"Please vefify your email address",
                    Toast.LENGTH_LONG).show()

            }
        }

        else {

           // Toast.makeText(this@SignInActivity,"Sign In failed, please try again",
             //       Toast.LENGTH_LONG).show()

        }

        // registration link
        register_txt.setOnClickListener {
            startActivity(Intent(this@SignInActivity,SignUpActivity::class.java))

        }

    }


}