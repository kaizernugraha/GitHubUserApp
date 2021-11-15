package com.dicoding.kaizer.githubuser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ListUsersAdapter(private val listUsers: ArrayList<Users>) : RecyclerView.Adapter<com.dicoding.kaizer.githubuser.ListUsersAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_users, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, username, location, company, repo, followers, following, photo) = listUsers[position]

        //circle image
        Glide.with(holder.itemView.context)
                .load(photo)
                .circleCrop()
                .into(holder.imgPhoto)
        holder.tvName.text = name
        holder.tvUsername.text = username
        holder.tvLocation_.text = location
        holder.tvCompany.text = company
        holder.tvRepo.text = repo
        holder.tvFollowers.text = followers
        holder.tvFollowing.text = following

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listUsers[holder.adapterPosition]) }

    }

    override fun getItemCount(): Int = listUsers.size

    //constrcutor
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgPhoto: ImageView = itemView.findViewById(R.id.item_users_img)
        var tvName: TextView = itemView.findViewById(R.id.item_users_name)
        var tvUsername: TextView = itemView.findViewById(R.id.item_users_username)
        var tvLocation_: TextView = itemView.findViewById(R.id.item_users_location)
        var tvCompany: TextView = itemView.findViewById(R.id.item_users_company)
        var tvRepo: TextView = itemView.findViewById(R.id.item_users_repository)
        var tvFollowers: TextView = itemView.findViewById(R.id.item_users_followers)
        var tvFollowing: TextView = itemView.findViewById(R.id.item_users_following)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Users)
    }

}