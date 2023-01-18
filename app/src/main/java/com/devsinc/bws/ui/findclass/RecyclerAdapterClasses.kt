package com.devsinc.bws.ui.findclass

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devsinc.bws.R
import com.devsinc.bws.model.ClassListItem

class RecyclerAdapterClasses(private val classList: List<ClassListItem>) :
    RecyclerView.Adapter<RecyclerAdapterClasses.ClassViewHolder>() {

    class ClassViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val ivClassImage: ImageView = itemView.findViewById(R.id.iv_book_class)
        val tvClassName: TextView = itemView.findViewById(R.id.tv_book_class_title)
        val tvClassDistance: TextView = itemView.findViewById(R.id.tv_distance)
        val linearLayout: LinearLayout = itemView.findViewById(R.id.linearIcons)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClassViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_find_class_item, parent, false)
        return ClassViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ClassViewHolder, position: Int) {
        val currentClass = classList[position]
        holder.tvClassName.text = currentClass.class_name
        holder.tvClassDistance.text = currentClass.distance
        Glide.with(holder.ivClassImage.context).load(currentClass.class_image)
            .into(holder.ivClassImage)

        holder.linearLayout.removeAllViews()
        for (i in 0 until currentClass.class_icon.size) {
            val imageView = ImageView(holder.linearLayout.context)
            imageView.layoutParams = LinearLayout.LayoutParams(100, 100)
            Glide.with(holder.linearLayout.context).load(currentClass.class_icon[i])
                .into(imageView)
            holder.linearLayout.addView(imageView)
        }
    }

    override fun getItemCount() = classList.size
}