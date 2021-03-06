package com.example.workwithretrofit2.adapters
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.workwithretrofit2.R
import kotlinx.android.synthetic.main.item_layout.view.*

class RecyclerAdapter(
    private var titles: List<String>,
    private var details: List<String>,
    private var images: List<String>,
    private var links: List<String>
) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTitle: TextView = itemView.tv_title
        val itemDescription: TextView = itemView.tv_description
        val itemImg: ImageView = itemView.iv_image

        init {
            itemView.setOnClickListener { v: View? ->
                val position: Int = adapterPosition
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(links[position])
                startActivity(itemView.context, intent, null)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemTitle.text = titles[position]
        holder.itemDescription.text = details[position]

        Glide.with(holder.itemImg)
            .load(images[position])
            .into(holder.itemImg)
    }
}