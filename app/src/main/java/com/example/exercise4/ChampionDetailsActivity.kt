package com.example.exercise4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.ui.AppBarConfiguration
import com.example.exercise4.databinding.ActivityChampionDetailsBinding

class ChampionDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityChampionDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChampionDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.CancelEditBtn.setOnClickListener { _ ->
            setResult(RESULT_CANCELED)
            finish()
        }

        binding.SaveEditBtn.setOnClickListener { _ ->
            var lane = Bundle().getInt("lane")
            var name = Bundle().getString("name")
            Log.d("ChampionDetailsActivity", "SaveEditBtn")
            Log.d("Bundle lane", lane.toString())
            Log.d("Bundle name", name.toString())
//            val intent = intent
//            intent.putExtra("champion", champion)
//            setResult(RESULT_OK, intent)
//            finish()
        }
    }
}