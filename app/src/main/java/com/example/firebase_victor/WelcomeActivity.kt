package com.example.firebase_victor

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.firebase_victor.databinding.ActivityMainBinding
import com.example.firebase_victor.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val preferences = getSharedPreferences(resources.getString(R.string.app_name), Context.MODE_PRIVATE)

        binding.tvWelcome.setText("WELCOME BACK: " + preferences.getString("email", ""))

        binding.btnMenu.setOnClickListener { view: View ->
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }

        binding.btnShowReviews.setOnClickListener { view: View ->
            val intent = Intent(this, SeeReviewActivity::class.java)
            startActivity(intent)
        }

    }
}