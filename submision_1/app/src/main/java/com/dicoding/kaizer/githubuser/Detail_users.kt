package com.dicoding.kaizer.githubuser

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Detail_users : AppCompatActivity() {

    companion object {
        const val EXTRA_PERSON = "extra_person"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_users)


        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val photo = findViewById<ImageView>(R.id.img_profile)
        val tvName = findViewById<TextView>(R.id.tv_name)
        val tvUsername = findViewById<TextView>(R.id.tv_username)
        val tvRepo = findViewById<TextView>(R.id.tv_repo)
        val tvCompany = findViewById<TextView>(R.id.tv_company)
        val tvFollowers = findViewById<TextView>(R.id.tv_followers)
        val tvFollowing = findViewById<TextView>(R.id.tv_following)
        val tvLocation = findViewById<TextView>(R.id.tv_location)


        val users = intent.getParcelableExtra<Users>(EXTRA_PERSON) as Users

        photo.setImageResource(users?.photo!!)
        val name = users.name
        val username = users.username
        val repository = users.repo
        val followers = users.followers
        val following = users.following
        val company = users.company
        val location = users.location


        tvName.text = name
        tvUsername.text = username
        tvRepo.text = repository
        tvFollowers.text = followers
        tvFollowing.text = following
        tvCompany.text = company
        tvLocation.text = location


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}