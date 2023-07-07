package com.example.growigh

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.example.growigh.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var restTimer: CountDownTimer? = null
    lateinit var binding : ActivityMainBinding
    private var restProgress = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        setupRestView()

        binding.skip.setOnClickListener {
            startActivity(Intent(this@MainActivity,welcome_screen::class.java))
            finish()
        }
    }

    private fun setupRestView() {

        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }

        setRestProgressBar()
    }
    private fun setRestProgressBar() {

        binding?.progressBar?.progress =
            restProgress // Sets the current progress to the specified value.

        restTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++ // It is increased by 1
                binding?.progressBar?.progress = 10 - restProgress
            }

            override fun onFinish() {
                startActivity(Intent(this@MainActivity,MainActivity2::class.java))
                finish()
            }
        }.start()
    }
    public override fun onDestroy() {
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        super.onDestroy()
    }
    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }

    fun nextscreen(view: View) {
        startActivity(Intent(this@MainActivity,MainActivity2::class.java))
        finish()
    }


}

