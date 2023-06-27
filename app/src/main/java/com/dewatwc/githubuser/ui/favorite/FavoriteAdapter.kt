package com.dewatwc.githubuser.ui.favorite

import android.content.Intent
import android.media.SoundPool
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dewatwc.githubuser.R
import com.dewatwc.githubuser.data.DatUser
import com.dewatwc.githubuser.ui.detail.DetailActivity
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_favorite.view.*

class FavoriteAdapter(private val Favorite: List<DatUser>) : RecyclerView.Adapter<FavoriteAdapter.UserHolder>() {

    private lateinit var sp: SoundPool
    private var soundId: Int = 0
    private var spLoaded = false

    inner class UserHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageAvatar: CircleImageView = itemView.avatar_favorite
        var name: TextView = itemView.name_favorite
        var company: TextView = itemView.company_favorite
        var location: TextView = itemView.location_favorite
        var username: TextView = itemView.user_name_favorite
        var repos: TextView = itemView.repo_favorite
        var followers: TextView = itemView.followers_favorite
        var following: TextView = itemView.following_favorite
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): UserHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_favorite, viewGroup, false)
        return UserHolder(view)
    }

    override fun getItemCount(): Int = Favorite.size

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        val data = Favorite[position]
        Glide.with(holder.itemView.context)
            .load(data.avatar)
            .apply(RequestOptions().override(250, 250))
            .apply(RequestOptions.placeholderOf(R.drawable.ic_account))
            .into(holder.imageAvatar)
        holder.name.text = data.name
        holder.company.text = data.company
        holder.username.text = data.username
        holder.location.text = data.location
        holder.followers.text = data.followers
        holder.following.text = data.following
        holder.repos.text = data.repositories

        sp = SoundPool.Builder()
            .setMaxStreams(10)
            .build()
        sp.setOnLoadCompleteListener { _, _, status ->
            if (status == 0) { spLoaded = true } }
        soundId = sp.load(holder.itemView.context, R.raw.klik, 1)

        holder.itemView.setOnClickListener {
            if (spLoaded) { sp.play(soundId, 1f, 1f, 0, 0, 2f) }
            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra(DetailActivity.EXTRA_DATA, data.username)
            holder.itemView.context.startActivity(intentDetail)
        }
    }
}



