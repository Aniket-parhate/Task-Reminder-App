package com.app.illremind

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.app.illremind.databinding.ActivitySplashBinding

class Splash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var handler: Handler
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        handler = Handler()
        handler.postDelayed({
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        },8000)

        }

}