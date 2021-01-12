package com.nadikarim.consumerapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nadikarim.consumerapp.R
import com.nadikarim.consumerapp.data.model.User
import com.nadikarim.consumerapp.databinding.ItemUserBinding

class FollowerFollowingAdapter(private var listUser: ArrayList<User>) : RecyclerView.Adapter<FollowerFollowingAdapter.UserViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size

    inner class UserViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(user.avatar)
                    .into(binding.imgAvatar)

                val usernameText: String?= "${resources.getString(R.string.username)}: ${user.username}"
                val nameText: String? = "${resources.getString(R.string.name)}: ${user.name}"
                val followersText: String? = "${resources.getString(R.string.followers)}: ${user.followers}"
                val followingText: String? = "${resources.getString(R.string.following)}: ${user.following}"

                binding.tvUsername.text = usernameText
                binding.tvName.text = nameText
                binding.tvFollowers.text = followersText
                binding.tvFollowing.text = followingText
            }
        }
    }

}