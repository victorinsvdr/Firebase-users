package com.example.firebase_victor

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase_victor.databinding.ActivitySeeReviewBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SeeReviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySeeReviewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySeeReviewBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val list = arrayListOf<ReviewModel>()
        val reviews = arrayListOf<ReviewModel>()

        val preferences =
            getSharedPreferences(resources.getString(R.string.app_name), Context.MODE_PRIVATE)

        val db = Firebase.firestore
        db.collection("reviews")
            .whereEqualTo("name", preferences.getString("email", ""))
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d("review", "${document.id} => ${document.data}")
                    val arr = document.data.values.toMutableList()
                    reviews.add(ReviewModel(arr[0].toString(), arr[2].toString()))

                }
                for (id in 0..reviews.size - 1) {
                    val product = reviews.get(id).product
                    val review = reviews.get(id).review

                    val reviewModel = ReviewModel(product, review)
                    list.add(reviewModel)
                    Log.d("listreview", "${list}")
                    //Log.d("FOOD", "${price}, $name")
                }

                binding.apply {

                    rvReviews.layoutManager = LinearLayoutManager(applicationContext)
                    rvReviews.adapter = applicationContext?.let { ReviewAdapter(it, list) }
                    rvReviews.addItemDecoration(
                        DividerItemDecoration(
                            applicationContext,
                            RecyclerView.VERTICAL
                        )
                    )
                }
            }
    }

}