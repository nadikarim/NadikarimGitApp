package com.nadikarim.consumerapp.ui

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.nadikarim.consumerapp.R
import com.nadikarim.consumerapp.data.model.Favorite
import com.nadikarim.consumerapp.databinding.ActivityDetailBinding
import com.nadikarim.consumerapp.utils.EXTRA_NOTE

class DetailActivity : AppCompatActivity(){
    private lateinit var binding: ActivityDetailBinding

    private lateinit var imageAvatar: String

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setDataObject()
        viewPagerConfig()
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
        val favoriteUser = intent.getParcelableExtra<Favorite>(EXTRA_NOTE) as Favorite
        favoriteUser.name?.let { setActionBarTitle(it) }

        val usernameText = "${resources.getString(R.string.username)}: ${favoriteUser.username}"
        val nameText = "${resources.getString(R.string.name)}: ${favoriteUser.name}"
        val companyText = "${resources.getString(R.string.company)}: ${favoriteUser.company}"
        val locationText = "${resources.getString(R.string.location)}: ${favoriteUser.location}"
        val repositoryText = "${resources.getString(R.string.repository)}: ${favoriteUser.repository}"
        val followersText = "${resources.getString(R.string.followers)}: ${favoriteUser.followers}"
        val followingText = "${resources.getString(R.string.following)}: ${favoriteUser.following}"

        binding.tvNamed.text = usernameText
        binding.tvUsernamed.text = nameText
        binding.tvCompany.text = companyText
        binding.tvLocationd.text = locationText
        binding.tvRepositoryd.text = repositoryText
        binding.tvFollowersd.text = followersText
        binding.tvFollowingd.text = followingText
        Glide.with(this)
            .load(favoriteUser.avatar)
            .into(binding.imgAvatarDetail)
        imageAvatar = favoriteUser.avatar.toString()
    }

}