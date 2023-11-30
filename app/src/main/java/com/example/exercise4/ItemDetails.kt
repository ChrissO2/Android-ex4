package com.example.exercise4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.exercise4.databinding.ActivityItemDetailsBinding

class ItemDetails : AppCompatActivity() {
    var binding:ActivityItemDetailsBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityItemDetailsBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        var plist:ChampionModel?=null

        if(intent.hasExtra(ListFragment.NEXT_SCREEN)){
            plist =
                intent.getSerializableExtra(ListFragment.NEXT_SCREEN) as ChampionModel
        }
        if(plist!=null){
            binding?.itemNameTextView?.text=plist.name
            when(plist.lane){
                Lane.TOP->{
                    binding?.itemImageView?.setImageResource(R.drawable.top_icon)
                }
                Lane.JUNGLE->{
                    binding?.itemImageView?.setImageResource(R.drawable.jungle_icon)
                }
                Lane.MID->{
                    binding?.itemImageView?.setImageResource(R.drawable.mid_icon)
                }
                Lane.BOTTOM->{
                    binding?.itemImageView?.setImageResource(R.drawable.adc_icon)
                }
                Lane.SUPPORT->{
                    binding?.itemImageView?.setImageResource(R.drawable.support_icon)
                }
            }
        }
        binding?.ratingBar2?.rating=plist?.rating!!
        binding?.descriptionTextView?.text=plist.description
        binding?.button?.setOnClickListener {
            finish()
        }
        binding?.deleteButton?.setOnClickListener {
            val builder = android.app.AlertDialog.Builder(this)
            builder.setTitle("Delete champion")
            builder.setMessage("Are you sure you want to delete this champion?")
            builder.setPositiveButton("Yes") { dialogInterface, which ->
                val intent = intent
                intent.putExtra("champion", plist)
                setResult(RESULT_OK, intent)
                finish()
            }
            builder.setNegativeButton("No") { dialogInterface, which ->
                dialogInterface.dismiss()
            }
            val alertDialog: android.app.AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()

        }
    }
}