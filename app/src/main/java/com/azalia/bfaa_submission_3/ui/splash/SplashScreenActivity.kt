package com.azalia.bfaa_submission_3.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.azalia.bfaa_submission_3.R
import com.azalia.bfaa_submission_3.databinding.ActivitySplashScreenBinding
import com.azalia.bfaa_submission_3.ui.main.MainActivity
import com.azalia.bfaa_submission_3.util.Constanta.TIME_SPLASH

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var splashScreenBinding: ActivitySplashScreenBinding
    private val viewModel: SplashScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashScreenBinding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(splashScreenBinding.root)

        val handler = Handler(mainLooper)

        handler.postDelayed({
            viewModel.getThemeSetting().observe(this@SplashScreenActivity, { isDarkModeActive ->
                if (isDarkModeActive) {
                    moveToMainActivity()
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    moveToMainActivity()
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            })
        }, TIME_SPLASH)

        supportActionBar?.hide()
    }

    fun moveToMainActivity() {
        val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}