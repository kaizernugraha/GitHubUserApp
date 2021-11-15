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
import com.google.android.material.snackbar.Snackbar

class DetailUsers : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    private lateinit var binding: ActivityDetailUsersBinding
    private lateinit var viewModel: DetailUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val username = intent.getStringExtra(EXTRA_DATA)
        val bundle = Bundle()
        bundle.putString(EXTRA_DATA, username)

        showLoading(true)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailUserViewModel::class.java)

        viewModel.setUserDetail(username.toString())
        viewModel.getUserDetail().observe(this, {
            if (it != null) {
                binding.apply {
                    it.name?.let { setActionBarTitle(it) }
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
        if (item.itemId == R.id.action_change_settings) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}