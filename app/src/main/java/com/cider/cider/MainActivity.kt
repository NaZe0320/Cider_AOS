package com.cider.cider

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cider.cider.databinding.ActivityMainBinding
import com.cider.cider.utils.binding.BindingActivity

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}