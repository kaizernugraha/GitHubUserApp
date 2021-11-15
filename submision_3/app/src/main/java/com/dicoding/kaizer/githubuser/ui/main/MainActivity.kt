package com.dicoding.kaizer.githubuser.ui.main

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.kaizer.githubuser.ui.detail.DetailUsers
import com.dicoding.kaizer.githubuser.R
import com.dicoding.kaizer.githubuser.data.model.Users
import com.dicoding.kaizer.githubuser.databinding.ActivityMainBinding
import com.dicoding.kaizer.githubuser.ui.favorite.FavoriteActivity
import com.dicoding.kaizer.githubuser.ui.settings.SettingActivity

class MainActivity : AppCompatActivity() {

    //variable
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etQuey.requestFocus()

        adapter = UsersAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object : UsersAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Users) {
                Intent(this@MainActivity, DetailUsers::class.java).also {
                    it.putExtra(DetailUsers.EXTRA_DATA, data.login)
                    it.putExtra(DetailUsers.EXTRA_ID, data.id)
                    it.putExtra(DetailUsers.EXTRA_URL, data.avatar_url)
                    startActivity(it)
                }
            }

        })
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        binding.apply {
            showRecyclerList()


            btnSearch.setOnClickListener {
                searchUser()
            }

            etQuey.setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    searchUser()
                    //comand
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
            viewModel.getSearchUsers().observe(this@MainActivity, {
                if (it != null) {
                    adapter.setList(it)
                    showLoading(false)
                }
            })
        }

    }


    private fun searchUser() {
        binding.apply {
            val query = etQuey.text.toString()
            if (query.isEmpty()) return
            showLoading(true)
            viewModel.setSearchUsers(query)
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showRecyclerList() {


        binding.apply {
            if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                rvGithubUser.layoutManager = GridLayoutManager(this@MainActivity, 2)
            } else {
                rvGithubUser.layoutManager = LinearLayoutManager(this@MainActivity)
            }

            rvGithubUser.setHasFixedSize(true)
            rvGithubUser.adapter = adapter
            rvGithubUser.startLayoutAnimation()

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
}