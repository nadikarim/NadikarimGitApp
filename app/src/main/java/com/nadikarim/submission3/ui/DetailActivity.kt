package com.nadikarim.submission3.ui

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.nadikarim.submission3.R
import com.nadikarim.submission3.data.db.DatabaseContract.FavoriteColumns.Companion.AVATAR
import com.nadikarim.submission3.data.db.DatabaseContract.FavoriteColumns.Companion.COMPANY
import com.nadikarim.submission3.data.db.DatabaseContract.FavoriteColumns.Companion.CONTENT_URI
import com.nadikarim.submission3.data.db.DatabaseContract.FavoriteColumns.Companion.FAVORITE
import com.nadikarim.submission3.data.db.DatabaseContract.FavoriteColumns.Companion.FOLLOWER
import com.nadikarim.submission3.data.db.DatabaseContract.FavoriteColumns.Companion.FOLLOWING
import com.nadikarim.submission3.data.db.DatabaseContract.FavoriteColumns.Companion.LOCATION
import com.nadikarim.submission3.data.db.DatabaseContract.FavoriteColumns.Companion.NAME
import com.nadikarim.submission3.data.db.DatabaseContract.FavoriteColumns.Companion.REPOSITORY
import com.nadikarim.submission3.data.db.DatabaseContract.FavoriteColumns.Companion.USERNAME
import com.nadikarim.submission3.data.db.FavoriteHelper
import com.nadikarim.submission3.data.model.Favorite
import com.nadikarim.submission3.data.model.User
import com.nadikarim.submission3.databinding.ActivityDetailBinding
import com.nadikarim.submission3.utils.EXTRA_DATA
import com.nadikarim.submission3.utils.EXTRA_NOTE

class DetailActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDetailBinding
    private var favorite : Favorite? = null
    private var isFavorite = false
    private lateinit var favoriteHelper: FavoriteHelper
    private lateinit var imageAvatar: String
    private lateinit var uriWithId: Uri



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        favoriteHelper = FavoriteHelper.getInstance(applicationContext)
        favoriteHelper.open()

        if (favorite != null) {
            setDataObject()
            isFavorite = true
            val checked: Int = R.drawable.ic_baseline_favorite_filled_24
            binding.btnFavorite.setImageResource(checked)
        } else {
            setData()
        }

        viewPagerConfig()
        binding.btnFavorite.setOnClickListener(this)
    }

    private fun viewPagerConfig() {
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        binding.viewPager.adapter = sectionsPagerAdapter
        binding.tab1.setupWithViewPager(binding.viewPager)

        supportActionBar?.elevation = 0f
    }

    private fun setActionBarTitle(title: String) {
        if (supportActionBar != null) {
            this.title = title
        }
    }

    private fun setData() {
        val dataUser = intent.getParcelableExtra<User>(EXTRA_DATA)
        dataUser?.name?.let { setActionBarTitle(it) }


        if (dataUser?.username != null) binding.tvUsernamed.text = dataUser.username.toString()
        if (dataUser?.name != null) binding.tvNamed.text = dataUser.name.toString()
        if (dataUser?.company != null) binding.tvCompany.text = dataUser.company.toString()
        if (dataUser?.location != null) binding.tvLocationd.text = dataUser.location.toString()
        if (dataUser?.repository != null) binding.tvRepositoryd.text = dataUser.repository.toString()
        if (dataUser?.followers != null) binding.tvFollowersd.text = dataUser.followers.toString()
        if (dataUser?.following != null) binding.tvFollowingd.text = dataUser.following.toString()

        Glide.with(this)
            .load(dataUser?.avatar)
            .into(binding.imgAvatarDetail)
        imageAvatar = dataUser?.avatar.toString()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_change_settings -> {
                val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(mIntent)
            }
            R.id.action_change_notification -> {
                val intent = Intent(this, NotifSettingActivity::class.java)
                startActivity(intent)
            }
            R.id.action_favorite -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_favorite) {
            if (isFavorite) {
                uriWithId = Uri.parse(CONTENT_URI.toString() + "/" + favorite?.username)
                //favoriteHelper.deleteById(favorite?.username.toString())
                contentResolver.delete(uriWithId, null, null)
                Toast.makeText(this, getString(R.string.delete_from_favorite), Toast.LENGTH_SHORT).show()
                binding.btnFavorite.setImageResource(R.drawable.ic_baseline_favorite_unfilled_24)
                isFavorite = false
            } else {
                val dataUsername = binding.tvUsernamed.text.toString()
                val dataName = binding.tvNamed.text.toString()
                val dataAvatar = imageAvatar
                val datacompany = binding.tvCompany.text.toString()
                val dataLocation = binding.tvLocationd.text.toString()
                val dataRepository = binding.tvRepositoryd.text.toString()
                val dataFolloewrs = binding.tvFollowersd.text.toString()
                val dataFollowing = binding.tvFollowingd.text.toString()
                val dataFavorite = "1"

                val values = ContentValues()
                values.put(USERNAME, dataUsername)
                values.put(NAME, dataName)
                values.put(AVATAR, dataAvatar)
                values.put(COMPANY, datacompany)
                values.put(LOCATION, dataLocation)
                values.put(REPOSITORY, dataRepository)
                values.put(FAVORITE, dataFavorite)
                values.put(FOLLOWER, dataFolloewrs)
                values.put(FOLLOWING, dataFollowing)

                contentResolver.insert(CONTENT_URI, values)
                Toast.makeText(this, getString(R.string.add_to_favorite), Toast.LENGTH_SHORT).show()
                binding.btnFavorite.setImageResource(R.drawable.ic_baseline_favorite_filled_24)
                isFavorite = true

            }
        }
    }

    private fun setDataObject() {
        val favoriteUser= intent.getParcelableExtra<Favorite>(EXTRA_NOTE)
        favoriteUser?.name?.let { setActionBarTitle(it) }
        binding.tvNamed.text = favoriteUser?.name.toString()
        binding.tvUsernamed.text = favoriteUser?.username.toString()
        binding.tvCompany.text = favoriteUser?.company.toString()
        binding.tvLocationd.text = favoriteUser?.location.toString()
        binding.tvRepositoryd.text = favoriteUser?.repository.toString()
        binding.tvFollowersd.text = favoriteUser?.followers.toString()
        binding.tvFollowingd.text = favoriteUser?.following.toString()
        Glide.with(this)
            .load(favoriteUser?.avatar)
            .into(binding.imgAvatarDetail)
        imageAvatar = favoriteUser?.avatar.toString()
    }

    override fun onDestroy() {
        super.onDestroy()
        favoriteHelper.close()
    }

}