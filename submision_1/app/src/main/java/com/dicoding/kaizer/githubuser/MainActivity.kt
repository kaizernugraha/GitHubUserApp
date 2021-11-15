package com.dicoding.kaizer.githubuser

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.kaizer.githubuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    companion object {
        const val EXTRA_PERSON = "extra_person"
        const val EXTRA_IMG = "extra_img"
    }

    //variable
    private lateinit var rvGithubUser: RecyclerView
    private val list = ArrayList<Users>()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvGithubUser = findViewById(R.id.rv_github_user)
        rvGithubUser.setHasFixedSize(true)

        list.addAll(listUsers)
        showRecyclerList()
    }



    private val listUsers: ArrayList<Users>
        get() {
            val dataName = resources.getStringArray(R.array.data_name)
            val dataUsername = resources.getStringArray(R.array.data_username)
            val dataLocation = resources.getStringArray(R.array.data_location)
            val dataCompany = resources.getStringArray(R.array.data_company)
            val dataRepository = resources.getStringArray(R.array.data_repository)
            val dataFollowers = resources.getStringArray(R.array.data_followers)
            val dataFollowing = resources.getStringArray(R.array.data_following)
            val dataPhoto = resources.obtainTypedArray(R.array.data_avatar)
            val listUsers = ArrayList<Users>()
            for (i in dataName.indices) {
                val users = Users(dataName[i], dataUsername[i], dataLocation[i], dataCompany[i], dataRepository[i], dataFollowers[i], dataFollowing[i], dataPhoto.getResourceId(i, -1))
                listUsers.add(users)
            }
            return listUsers
        }

    private fun showRecyclerList() {

        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rvGithubUser.layoutManager = GridLayoutManager(this, 2)
        } else {
            rvGithubUser.layoutManager = LinearLayoutManager(this)
        }

        val listUsersAdapter = ListUsersAdapter(list)
        rvGithubUser.adapter = listUsersAdapter

        listUsersAdapter.setOnItemClickCallback(object : ListUsersAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Users) {
                showSelectedHero(data)
            }
        })
    }

    private fun showSelectedHero(users: Users) {

        val moveWithDataIntent  = Intent(this@MainActivity,Detail_users::class.java)
        moveWithDataIntent.putExtra(MainActivity.EXTRA_PERSON, users)
        moveWithDataIntent.putExtra(MainActivity.EXTRA_IMG, users)
        startActivity(moveWithDataIntent)

    }
}