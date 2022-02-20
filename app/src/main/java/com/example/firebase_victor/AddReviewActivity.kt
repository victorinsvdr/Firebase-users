package com.example.firebase_victor

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.firebase_victor.databinding.ActivityAddReviewBinding
import com.example.firebase_victor.databinding.ActivityMainBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddReviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddReviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_review)

        binding = ActivityAddReviewBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val name = intent.getStringExtra("product")

        Toast.makeText(applicationContext, "$name", Toast.LENGTH_LONG).show()

        binding.tvProduct.text = name!!

        binding.btnSaveReview.setOnClickListener { view: View ->
            //TODO PERSIST REVIEW INTO THE DB

            val preferences = getSharedPreferences(resources.getString(R.string.app_name), Context.MODE_PRIVATE)
            val review = hashMapOf(
                "name" to preferences.getString("email", ""),
                "product" to binding.tvProduct.text.toString(),
                "text" to binding.etReview.text.toString()
            )



            val db = Firebase.firestore
            db.collection("reviews")
                .add(review)
                .addOnSuccessListener {
                    Log.d(
                        "addreview",
                        "DocumentSnapshot successfully written!"
                    )
                }
                .addOnFailureListener { e ->
                    Log.w(
                        "addreview",
                        "Error writing document",
                        e
                    )
                }


            if(binding.etReview.text.isNotEmpty())
                view.context.startActivity(Intent(view.context, WelcomeActivity::class.java))
            else
                Toast.makeText(applicationContext, "Please, fill the review field.", Toast.LENGTH_LONG).show()
        }
    }
}