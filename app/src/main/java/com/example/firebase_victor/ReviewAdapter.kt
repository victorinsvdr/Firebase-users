package com.example.firebase_victor

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReviewAdapter(
    private val context: Context,
    private val list: ArrayList<ReviewModel>,
) : RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val product: TextView = view.findViewById(R.id.tv_review_product)
        val review: TextView = view.findViewById(R.id.tv_review_review)


        /*init {
            itemView.setOnClickListener { view: View ->
                val position: Int = adapterPosition
                //Toast.makeText(itemView.context, "${name.text}", Toast.LENGTH_LONG).show()
                //val intent = Intent(this, WelcomeActivity::class.java)

                //TODO PASS NAME THROUGH INTENT.
                val intent = Intent(view.context, AddReviewActivity::class.java)
                intent.putExtra("product", name.text)
                view.context.startActivity(intent)


        }*/
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.review_item, parent, false)
        Log.d("REVIEWS", "$list")
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.product.text = data.product
        holder.review.text = data.review
    }


}