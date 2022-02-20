package com.example.firebase_victor

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.firebase_victor.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnLogin.setOnClickListener { view: View ->

            //TODO REMOVE COMMENTS WHEN ALL IS OK.
            //val intent = Intent(this, WelcomeActivity::class.java)
            //startActivity(intent)

            auth.signInWithEmailAndPassword(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            )
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.w("LOGIN", "signInWithEmail:correct", task.exception)
                        Toast.makeText(
                            baseContext, "Authentication is correct.",
                            Toast.LENGTH_SHORT
                        ).show()

                        val preference = getSharedPreferences(
                            resources.getString(R.string.app_name),
                            Context.MODE_PRIVATE
                        )
                        val editor = preference.edit()
                        editor.putString("email", binding.etEmail.text.toString())
                        editor.apply()

                        val user = hashMapOf(
                            "name" to binding.etEmail.text.toString()
                        )


                        //ADD THE USER MAIL AS DOCUMENT IN DB
                        val db = Firebase.firestore
                        db.collection("users").document(binding.etEmail.text.toString())
                            .set(user)
                            .addOnSuccessListener {
                                Log.d(
                                    "adduser",
                                    "DocumentSnapshot successfully written!"
                                )
                            }
                            .addOnFailureListener { e ->
                                Log.w(
                                    "aduser",
                                    "Error writing document",
                                    e
                                )
                            }


                        val intent = Intent(this, WelcomeActivity::class.java)
                        startActivity(intent)
                    } else {
                        Log.w("LOGIN", "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
        auth = Firebase.auth
        //createUser("victor@institutvidreres.cat", "test123123")
        //signIn("victor@institutvidreres.cat", "test123123")


    }

    fun insertUser(db: FirebaseFirestore, user: HashMap<String, String>) {
        db.collection("users")
            .document("Enaitz")
            .set(user)
            .addOnSuccessListener { documentReference ->
                Log.d("FIRESTOREDB", "DocumentSnapshot added")
            }
            .addOnFailureListener { e ->
                Log.w("FIRESTOREDB", "Error adding document", e)
            }
    }

    fun getUsers(db: FirebaseFirestore) {
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("READ", "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("READ", "Error getting documents.", exception)
            }
    }

    fun createUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("AUTHENTICATION", "createUserWithEmail:success")
                    val user = auth.currentUser
                } else {
                    Log.w("AUTHENTICATION", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}