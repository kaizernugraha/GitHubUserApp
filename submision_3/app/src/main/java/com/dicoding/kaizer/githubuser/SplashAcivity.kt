package com.dicoding.kaizer.githubuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import com.dicoding.kaizer.githubuser.databinding.ActivitySplashAcivityBinding
import com.dicoding.kaizer.githubuser.ui.main.MainActivity

class SplashAcivity : AppCompatActivity() {

    companion object {
        private const val TIME_DELAY = 3000
    }

    private lateinit var binding: ActivitySplashAcivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashAcivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sideAnimation = AnimationUtils.loadAnimation(this, R.anim.slide)
        binding.tvTitle.startAnimation(sideAnimation)

        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, TIME_DELAY.toLong())

    }
}






