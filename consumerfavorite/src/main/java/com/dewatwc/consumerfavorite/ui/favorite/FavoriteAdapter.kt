package com.dewatwc.consumerfavorite.ui.favorite

import android.content.Intent
import android.media.SoundPool
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dewatwc.consumerfavorite.R
import com.dewatwc.consumerfavorite.data.DataUser
import com.dewatwc.consumerfavorite.ui.detail.DetailActivity
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_user.view.*

class FavoriteAdapter(private var listData: ArrayList<DataUser>) : RecyclerView.Adapter<FavoriteAdapter.UserHolder>() {

    private lateinit var sp: SoundPool
    private var soundId: Int = 0
    private var spLoaded = false

    inner class UserHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageAvatar: CircleImageView = itemView.avatar
        var name: TextView = itemView.user_name
        var company: TextView = itemView.company
        var html: TextView = itemView.html
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): UserHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_user, viewGroup, false)
        return UserHolder(view)
    }

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        val data = listData[position]
        Glide.with(holder.itemView.context)
            .load(data.avatar)
            .apply(RequestOptions().override(250, 250))
            .apply(RequestOptions.placeholderOf(R.drawable.ic_account))
            .into(holder.imageAvatar)
        holder.name.text = data.name
        holder.company.text = data.company
        holder.html.text = data.html_url

        sp = SoundPool.Builder()
            .setMaxStreams(10)
            .build()
        sp.setOnLoadCompleteListener { _, _, status ->
            if (status == 0) { spLoaded = true }
        }
        soundId = sp.load(holder.itemView.context, R.raw.click, 1)

        holder.itemView.setOnClickListener {
            if (spLoaded) { sp.play(soundId, 1f, 1f, 0, 0, 2f) }
            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra(DetailActivity.EXTRA_DATA,  data.username)
            holder.itemView.context.startActivity(intentDetail)
        }
    }
}