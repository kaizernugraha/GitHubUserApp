package com.dicoding.kaizer.githubuser.ui.detail

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dicoding.kaizer.githubuser.R
import com.dicoding.kaizer.githubuser.SectionPagerAdapter
import com.dicoding.kaizer.githubuser.databinding.ActivityDetailUsersBinding
import com.dicoding.kaizer.githubuser.ui.favorite.FavoriteActivity
import com.dicoding.kaizer.githubuser.ui.settings.SettingActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUsers : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_URL = "extra_url"
    }

    private lateinit var binding: ActivityDetailUsersBinding

    private lateinit var viewModel: DetailUserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val username = intent.getStringExtra(EXTRA_DATA)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_URL)
        val bundle = Bundle()
        bundle.putString(EXTRA_DATA, username)

        showLoading(true)
        viewModel = ViewModelProvider(this).get(DetailUserViewModel::class.java)

        viewModel.setUserDetail(username.toString())
        viewModel.getUserDetail().observe(this, {
            if (it != null) {
                binding.apply {
                    setActionBarTitle(it.name)
                    tvName.text = it.name
                    tvUsername.text = it.login
                    tvRepo.text = it.repository.toString()
                    tvCompany.text = it.company
                    tvLocation.text = it.location
                    tvFollowers.text = it.followers.toString()
                    tvFollowing.text = it.following.toString()
                    Glide.with(this@DetailUsers)
                        .load(it.avatar_url)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .placeholder(R.drawable.icon_person)
                        .error(R.drawable.icon_person)
                        .into(imgProfile)
                }
                showLoading(false)
                viewModel.toasText.observe(this@DetailUsers, {
                    it.getContentIfNotHandled()?.let { toast ->
                        Toast.makeText(
                            this, R.string.show_message_succes, Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }
        })


        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    if (count > 0) {
                        binding.toggleFavorite.isChecked = true
                        _isChecked = true
                    } else {
                        binding.toggleFavorite.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }



        binding.toggleFavorite.setOnClickListener {

            Toast.makeText(this@DetailUsers, R.string.add_favorite, Toast.LENGTH_SHORT).show()
            _isChecked = !_isChecked
            if (_isChecked) {
                if (username != null) {
                    if (avatarUrl != null) {
                        viewModel.addToFavorite(username, id, avatarUrl)
                    }

                }
            } else {
                viewModel.removeFromFavorite(id)
                Toast.makeText(this@DetailUsers, R.string.delete_favorite, Toast.LENGTH_SHORT)
                    .show()
            }
            binding.toggleFavorite.isChecked = _isChecked
        }


        val sectionsPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = sectionsPagerAdapter
            tabs.setupWithViewPager(viewPager)
            supportActionBar?.elevation = 0f
        }


    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBarDetailUser.visibility = View.VISIBLE
        } else {
            binding.progressBarDetailUser.visibility = View.GONE
        }
    }

    private fun setActionBarTitle(title: String) {
        if (supportActionBar != null) {
            this.title = title
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite_menu -> {
                Intent(this, FavoriteActivity::class.java).also {
                    startActivity(it)
                }
            }
            R.id.setting_menu -> {
                Intent(this, SettingActivity::class.java).also {
                    startActivity(it)
                }
            }
            R.id.action_change_settings -> {
                val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(mIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}