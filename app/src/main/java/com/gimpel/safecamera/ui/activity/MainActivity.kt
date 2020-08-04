package com.gimpel.safecamera.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gimpel.safecamera.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
    }

    fun setActionBarVisibility(isVisible: Boolean) {
        if (isVisible) supportActionBar?.show() else supportActionBar?.hide()
    }
}