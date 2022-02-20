package com.example.firebase_victor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.firebase_victor.databinding.ActivityMenuBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val db = Firebase.firestore
        val list = arrayListOf<FoodModel>()

        //FIXME REQUEST FIREBASE TO GET ALL FOOD FROM PRODUCTS COLLECTION.

        val drinks = arrayListOf<FoodModel>()

        val storageRef = Firebase.storage.reference

        db.collection("products")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("getfood", "${document.id} => ${document.data.values.toMutableList()}")
                    val arr = document.data.values.toMutableList()
                    drinks.add(FoodModel(arr[0].toString(), arr[1].toString()))
                }
                for (id in 0..drinks.size - 1) {
                    val price = drinks.get(id).price
                    val name = drinks.get(id).name

                    val drinkModel = FoodModel(
                        price,
                        name
                    )
                    list.add(drinkModel)
                }

                binding.apply {
                    rvMenu.layoutManager = LinearLayoutManager(applicationContext)
                    rvMenu.adapter = applicationContext?.let {
                        Adapter(it, list)
                    }
                    rvMenu.addItemDecoration(
                        DividerItemDecoration(
                            applicationContext,
                            RecyclerView.VERTICAL
                        )
                    )
                }

            }
            .addOnFailureListener { exception ->
                Log.w("getfood", "Error getting documents.", exception)
            }
    }
}