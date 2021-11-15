package com.dicoding.kaizer.githubuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.ViewPropertyAnimator
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView

class SplashAcivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_acivity)

        val tv_Title : TextView = findViewById(R.id.tv_title)
        val sideAnimation = AnimationUtils.loadAnimation(this, R.anim.slide)
        tv_Title.startAnimation(sideAnimation)

        Handler().postDelayed({
             startActivity(Intent(this,MainActivity::class.java))
            finish()
        }, 3000)

    }
}






