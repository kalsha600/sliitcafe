package com.example.scafe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val signInButton = findViewById<Button>(R.id.signin_btn)
        signInButton.setOnClickListener {
            val Intent = Intent(this, SignInActivity::class.java)
            startActivity(Intent)
        }


        val signupButton = findViewById<Button>(R.id.signup_btn)
        signupButton.setOnClickListener {
            val Intent = Intent(this, SignUpActivity::class.java)
            startActivity(Intent)
        }

    }
}