package com.dicoding.kaizer.githubuser.ui.favorite

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.kaizer.githubuser.R
import com.dicoding.kaizer.githubuser.data.local.FavoriteUser
import com.dicoding.kaizer.githubuser.data.model.Users
import com.dicoding.kaizer.githubuser.databinding.ActivityFavoriteBinding
import com.dicoding.kaizer.githubuser.ui.detail.DetailUsers
import com.dicoding.kaizer.githubuser.ui.main.UsersAdapter

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setActionBarTitle()

        adapter = UsersAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        adapter.setOnItemClickCallback(object : UsersAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Users) {
                Intent(this@FavoriteActivity, DetailUsers::class.java).also {
                    it.putExtra(DetailUsers.EXTRA_DATA, data.login)
                    it.putExtra(DetailUsers.EXTRA_ID, data.id)
                    it.putExtra(DetailUsers.EXTRA_URL, data.avatar_url)
                    startActivity(it)
                }
            }

        })

        showRecyclerList()

        viewModel.getFavoriteUser()?.observe(this, {
            if (it != null) {
                val list = mapList(it)
                adapter.setList(list)
                showLoading(false)
            } else {
                adapter.setList(ArrayList(it))
                showSnackbarMessage()
                showLoading(false)
            }
        })
    }


    private fun showRecyclerList() {

        binding.apply {
            if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                rvUserFav.layoutManager = GridLayoutManager(this@FavoriteActivity, 2)
            } else {
                rvUserFav.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            }

            rvUserFav.setHasFixedSize(true)
            rvUserFav.adapter = adapter
            rvUserFav.startLayoutAnimation()
        }


    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun mapList(users: List<FavoriteUser>): ArrayList<Users> {
        val listUsers = ArrayList<Users>()
        for (user in users) {
            val userMapper = Users(
                user.login,
                user.id,
                user.avatar_url

            )
            listUsers.add(userMapper)
        }
        return listUsers
    }

    private fun showSnackbarMessage() {
        Toast.makeText(this, getString(R.string.empty_favorite), Toast.LENGTH_SHORT).show()
    }

    private fun setActionBarTitle() {
        if (supportActionBar != null) {
            supportActionBar?.title = "Github Favorite Users"
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}