package com.nadikarim.submission3.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.nadikarim.submission3.data.model.Favorite
import com.nadikarim.submission3.data.model.User
import com.nadikarim.submission3.databinding.FragmentFollowingBinding
import com.nadikarim.submission3.utils.*
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class FollowingFragment : Fragment() {

    companion object {
        private val TAG = FollowingFragment::class.java.simpleName
    }

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private var listUser: ArrayList<User> = ArrayList()
    private var favorites: Favorite? = null
    private lateinit var adapter: FollowerFollowingAdapter
    private lateinit var favoriteData: Favorite
    private lateinit var user: User

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = FollowerFollowingAdapter(listUser)
        listUser.clear()
        favorites = activity!!.intent.getParcelableExtra(EXTRA_NOTE)
        if (favorites != null) {
            favoriteData = activity!!.intent.getParcelableExtra<Favorite>(EXTRA_NOTE) as Favorite
            getUserFollowing(favoriteData.username.toString())
        } else {
            user = activity!!.intent.getParcelableExtra<User>(EXTRA_DATA) as User
            getUserFollowing(user.username.toString())
        }
    }

    private fun getUserFollowing(id: String) {
        binding.progressBarFollowing.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        client.addHeader("User-Agent", "request")
        client.addHeader("Authorization", TOKEN)
        val url = "https://api.github.com/users/$id/following"
        client.get(url, object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                binding.progressBarFollowing.visibility = View.INVISIBLE
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val jsonArray = JSONArray(result)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val username: String = jsonObject.getString("login")
                        getUserDetail(username)
                    }
                } catch (e: Exception) {
                    Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                binding.progressBarFollowing.visibility = View.INVISIBLE
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : $ERROR_401"
                    403 -> "$statusCode : $ERROR_403"
                    404 -> "$statusCode : $ERROR_404"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun getUserDetail(id: String) {
        binding.progressBarFollowing.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        client.addHeader("User-Agent", "request")
        client.addHeader("Authorization", TOKEN)
        val url = "https://api.github.com/users/$id"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                binding.progressBarFollowing.visibility = View.INVISIBLE
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val jsonObject = JSONObject(result)
                    val username: String = jsonObject.getString("login").toString()
                    val name: String = jsonObject.getString("name").toString()
                    val avatar: String = jsonObject.getString("avatar_url").toString()
                    val company: String = jsonObject.getString("company").toString()
                    val location: String = jsonObject.getString("location").toString()
                    val repository: String? = jsonObject.getString("public_repos")
                    val followers: String? = jsonObject.getString("followers")
                    val following: String? = jsonObject.getString("following")
                    listUser.add(
                        User(
                            username,
                            name,
                            avatar,
                            company,
                            location,
                            repository,
                            followers,
                            following
                        )
                    )
                    showRecyclerList()
                } catch (e: java.lang.Exception) {
                    Toast.makeText(activity, e.message, Toast.LENGTH_SHORT)
                        .show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                binding.progressBarFollowing.visibility = View.INVISIBLE
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : $ERROR_401"
                    403 -> "$statusCode : $ERROR_403"
                    404 -> "$statusCode : $ERROR_404"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

    private fun showRecyclerList() {
        binding.rvFollowing.layoutManager = LinearLayoutManager(activity)
        binding.rvFollowing.setHasFixedSize(true)
        binding.rvFollowing.adapter = adapter
    }

}