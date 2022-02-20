package com.example.firebase_victor

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter(
    private val context: Context,
    private val list: ArrayList<FoodModel>,
) : RecyclerView.Adapter<Adapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val price: TextView = view.findViewById(R.id.tv_review_product)
        val name: TextView = view.findViewById(R.id.tv_review_review)
        //val image: ImageView = view.findViewById(R.id.iv_image)

        init {
            itemView.setOnClickListener { view: View ->
                val position: Int = adapterPosition
                //Toast.makeText(itemView.context, "${name.text}", Toast.LENGTH_LONG).show()
                //val intent = Intent(this, WelcomeActivity::class.java)

                //TODO PASS NAME THROUGH INTENT.
                val intent = Intent(view.context, AddReviewActivity::class.java)
                intent.putExtra("product", name.text)
                view.context.startActivity(intent)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item, parent, false)
        Log.d("FOOD", "$list")
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.price.text = data.price.toString() + "€"
        holder.name.text = data.name
    }


}