package com.nadikarim.submission3

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nadikarim.submission3.data.model.User
import com.nadikarim.submission3.databinding.ItemUserBinding
import com.nadikarim.submission3.utils.EXTRA_FAVORITE
import java.util.*
import kotlin.collections.ArrayList

var userFilterList = ArrayList<User>()
class UserAdapter(private var listUser: ArrayList<User>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>(), Filterable {
    private var onItemClickCallback: OnItemClickCallback? = null


    init {
        userFilterList = listUser
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = userFilterList.size

    inner class UserViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(user: User) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(user.avatar)
                    .apply(RequestOptions().override(100, 100))
                    .into(binding.imgAvatar)

                val usernameText = "${resources.getString(R.string.username)}: ${user.username}"
                val nameText = "${resources.getString(R.string.name)}: ${user.name}"
                val followerText = "${resources.getString(R.string.followers)}: ${user.followers}"
                val followingText = "${resources.getString(R.string.following)}: ${user.following}"

                binding.tvUsername.text = usernameText
                binding.tvName.text = nameText
                binding.tvFollowers.text = followerText
                binding.tvFollowing.text = followingText

                itemView.setOnClickListener {
                    @Suppress("NAME_SHADOWING") val user = User(
                        user.username,
                        user.name,
                        user.avatar,
                        user.company,
                        user.location,
                        user.repository,
                        user.followers,
                        user.following,
                        user.isFav
                    )
                    val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_DATA, user)
                    intent.putExtra(EXTRA_FAVORITE, user)
                    context.startActivity(intent)
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val charSearch = constraint.toString()
                userFilterList = if (charSearch.isEmpty()) {
                    listUser
                } else {
                    val listResult = ArrayList<User>()
                    for (userRow in userFilterList) {
                        if ((userRow.username.toString().toLowerCase(Locale.ROOT)
                                        .contains(charSearch.toLowerCase(Locale.ROOT)))
                        ) {
                            listResult.add(
                                    User( userRow.username, userRow.name, userRow.avatar, userRow.company, userRow.location, userRow.repository, userRow.followers, userRow.following)
                            )
                        }
                    }
                    listResult
                }
                val filterResults = FilterResults()
                filterResults.values = userFilterList
                return filterResults
            }


            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                userFilterList = results.values as ArrayList<User>
                notifyDataSetChanged()
            }
        }
    }
}