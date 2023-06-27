package com.dewatwc.consumerfavorite.ui.detail.follow

import `in`.abhisheksaxena.toaster.Toaster
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dewatwc.consumerfavorite.R
import com.dewatwc.consumerfavorite.data.DataUser
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_user.view.*

class FollowAdapter(private val listData: ArrayList<DataUser>): RecyclerView.Adapter<FollowAdapter.FollowHolder>() {

    fun clearItems() {
        listData.clear()
        notifyDataSetChanged()
    }
    
    inner class FollowHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageAvatar: CircleImageView = itemView.avatar
        var name: TextView = itemView.user_name
        var company: TextView = itemView.company
        var html: TextView = itemView.html
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): FollowHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_user, viewGroup, false)
        return FollowHolder(view)
    }

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: FollowHolder, position: Int) {
        val data = listData[position]
        Glide.with(holder.itemView.context)
            .load(data.avatar)
            .apply(RequestOptions().override(250, 250))
            .apply(RequestOptions.placeholderOf(R.drawable.ic_account))
            .into(holder.imageAvatar)
        holder.name.text = data.username
        holder.company.text = data.company
        holder.html.text  = data.html_url
        holder.itemView.setOnClickListener {

            Toaster.pop(holder.itemView.context, data.username.toString(), R.drawable.ic_account, Toaster.LENGTH_SHORT).show()

        }
    }
}