package com.nadikarim.consumerapp

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nadikarim.consumerapp.data.model.Favorite
import com.nadikarim.consumerapp.databinding.ItemUserBinding
import com.nadikarim.consumerapp.utils.CustomOnItemClickListener
import com.nadikarim.consumerapp.utils.EXTRA_NOTE
import com.nadikarim.consumerapp.utils.EXTRA_POSITION
import java.util.ArrayList

class ListFavoriteAdapter(private val activity: Activity) : RecyclerView.Adapter<ListFavoriteAdapter.ListViewHolder>() {
    var listFavorite = ArrayList<Favorite>()
        set(listFavorite) {
            if (listFavorite.size > 0) {
                this.listFavorite.clear()
            }
            this.listFavorite.addAll(listFavorite)

            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listFavorite[position])
    }

    override fun getItemCount(): Int = listFavorite.size

    inner class ListViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: Favorite) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(favorite.avatar)
                    .apply(RequestOptions())
                    .into(binding.imgAvatar)
                val usernameText: String?= "${resources.getString(R.string.username)}: ${favorite.username}"
                val nameText: String? = "${resources.getString(R.string.name)}: ${favorite.name}"
                val followersText: String? = "${resources.getString(R.string.followers)}: ${favorite.followers}"
                val followingText: String? = "${resources.getString(R.string.following)}: ${favorite.following}"

                binding.tvUsername.text = usernameText
                binding.tvName.text = nameText
                binding.tvFollowers.text = followersText
                binding.tvFollowing.text = followingText
                itemView.setOnClickListener(
                    CustomOnItemClickListener(
                        adapterPosition, object : CustomOnItemClickListener.OnItemClickCallback {
                            override fun onItemClicked(view: View, position: Int) {
                                val intent = Intent(context, DetailActivity::class.java)
                                intent.putExtra(EXTRA_POSITION, position)
                                intent.putExtra(EXTRA_NOTE, favorite)
                                context.startActivity(intent)
                            }
                        }
                    )
                )
            }
        }
    }
}