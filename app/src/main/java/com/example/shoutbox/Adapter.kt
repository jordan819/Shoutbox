package com.example.shoutbox


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class Adapter(
        private val commentsList: List<Comment>,
        ):
        RecyclerView.Adapter<Adapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.comment_layout,
            parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var currentComment = commentsList[position]

        holder.content.text = currentComment.content
        holder.author.text = currentComment.author
        holder.date.text = currentComment.date

        //holder.itemView.findViewById<TextView>(R.id.content).text = currentComment.content
    }

    override fun getItemCount() = commentsList.size


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val content: TextView = itemView.findViewById(R.id.content)
        val author: TextView = itemView.findViewById(R.id.author)
        val date: TextView = itemView.findViewById(R.id.date)

    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}