package com.example.exercise4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.exercise4.databinding.ActivityChampionAddBinding

class ChampionAddActivity : AppCompatActivity() {
    lateinit var binding: ActivityChampionAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChampionAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.saveButton.setOnClickListener { _ ->
            var champion_lane = Lane.TOP
            when (binding.radioGroup.checkedRadioButtonId) {
                R.id.topRadioButton -> champion_lane = Lane.TOP
                R.id.jungleRadioButton -> champion_lane = Lane.JUNGLE
                R.id.midRadioButton -> champion_lane = Lane.MID
                R.id.botRadioButton -> champion_lane = Lane.BOTTOM
                R.id.supportRadioButton -> champion_lane = Lane.SUPPORT
            }
            val champion = ChampionModel(
                binding.editName.text.toString(),
                binding.editTextTextMultiLine.text.toString(),
                champion_lane,
                binding.ratingBar3.rating,
            )
            val intent = intent
            intent.putExtra("champion", champion)
            setResult(RESULT_OK, intent)
            finish()


        }
        binding.cancelButton.setOnClickListener { _ ->
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}