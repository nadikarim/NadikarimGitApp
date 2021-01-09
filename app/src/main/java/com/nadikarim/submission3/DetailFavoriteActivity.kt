package com.nadikarim.submission3

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
import com.nadikarim.submission3.data.db.DatabaseContract
import com.nadikarim.submission3.data.db.DatabaseContract.FavoriteColumns.Companion.CONTENT_URI
import com.nadikarim.submission3.data.db.FavoriteHelper
import com.nadikarim.submission3.data.model.Favorite
import com.nadikarim.submission3.databinding.ActivityDetailFavoriteBinding
import com.nadikarim.submission3.utils.EXTRA_NOTE

class DetailFavoriteActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailFavoriteBinding
    private var favorite : Favorite? = null
    private var isFavorite = true
    private lateinit var favoriteHelper: FavoriteHelper
    private lateinit var imageAvatar: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favoriteHelper = FavoriteHelper.getInstance(applicationContext)
        favoriteHelper.open()

        setDataObject()
        viewPagerConfig()
        binding.btnFavorite.setImageResource(R.drawable.ic_baseline_favorite_filled_24)
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
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setDataObject() {
        val favoriteUser = intent.getParcelableExtra<Favorite>(EXTRA_NOTE)
        favoriteUser?.name?.let { setActionBarTitle(it) }

        val usernameText = "${resources.getString(R.string.username)}: ${favoriteUser?.username}"
        val nameText = "${resources.getString(R.string.name)}: ${favoriteUser?.name}"
        val companyText = "${resources.getString(R.string.company)}: ${favoriteUser?.company}"
        val locationText = "${resources.getString(R.string.location)}: ${favoriteUser?.location}"
        val repositoryText = "${resources.getString(R.string.repository)}: ${favoriteUser?.repository}"
        val followersText = "${resources.getString(R.string.followers)}: ${favoriteUser?.followers}"
        val followingText = "${resources.getString(R.string.following)}: ${favoriteUser?.following}"

        binding.tvNamed.text = usernameText
        binding.tvUsernamed.text = nameText
        binding.tvCompany.text = companyText
        binding.tvLocationd.text = locationText
        binding.tvRepositoryd.text = repositoryText
        binding.tvFollowersd.text = followersText
        binding.tvFollowingd.text = followingText
        Glide.with(this)
                .load(favoriteUser?.avatar)
                .into(binding.imgAvatarDetail)
        imageAvatar = favoriteUser?.avatar.toString()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_favorite -> {
                if (isFavorite) {
                    val uriId =Uri.parse(CONTENT_URI.toString() + "/" + favorite?.username)
                    contentResolver.delete(uriId, null, null)
                    /*
                    Yang ini juga gak bisa
                    favoriteHelper.deleteById(favorite?.username.toString())
                     */
                    Toast.makeText(this, getString(R.string.delete_from_favorite), Toast.LENGTH_SHORT).show()
                    binding.btnFavorite.setImageResource(R.drawable.ic_baseline_favorite_unfilled_24)
                    isFavorite = false
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        favoriteHelper.close()
    }
}