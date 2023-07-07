package com.example.growigh

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.graphics.Insets.add
import android.nfc.Tag
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.cryptotracker.model
import com.example.growigh.databinding.ActivityNewfeedBinding
import kotlin.collections.ArrayList

class newfeed : AppCompatActivity() {

    val memeList = ArrayList<model>()
    lateinit var binding: ActivityNewfeedBinding

    private lateinit var rvapdapter: MemeAdapter

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewfeedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        binding.pb.visibility= View.VISIBLE
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbar.setNavigationOnClickListener {
            startActivity(Intent(this@newfeed,welcome_screen::class.java))
        }


        rvapdapter= MemeAdapter(this,memeList)
        binding.rv.layoutManager=LinearLayoutManager(this)
        binding.rv.adapter=rvapdapter
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.rgb(18, 81, 120)
        }
        val url = "https://meme-api.com/gimme/20"

        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                        val jsonArray = response.getJSONArray("memes")

                        for (i in 0 until jsonArray.length()) {
                            val memeObject = jsonArray.getJSONObject(i)

                            val memes=model(memeObject.getString("url"))

                            binding.pb.visibility= View.INVISIBLE
                            memeList.add(memes)

                            Log.d(TAG,memeList.toString())
                        }
                    val memeAdapter = MemeAdapter(this, memeList)
                    binding.rv.adapter = memeAdapter
                }catch (e: Exception) {
                    e.printStackTrace()
                }
            },
            { error ->
                binding.pb.visibility= View.INVISIBLE
                Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
            })
        MySingleton.getInstance(this).addToRequestQueue(request)
    }

    var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }
}

