package com.example.exercise4

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.util.Objects


class ImagePagerAdapter: PagerAdapter() {
    var context: Context? = null

    lateinit var images: IntArray

    var mLayoutInflater: LayoutInflater? = null


    fun ViewPagerAdapter(context: Context, images: IntArray) {
        this.context = context
        this.images = images
        mLayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getCount(): Int {
        return images.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as LinearLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView: View = mLayoutInflater!!.inflate(R.layout.image_item, container, false)

        val imageView = itemView.findViewById<View>(R.id.imageViewMain) as ImageView

        imageView.setImageResource(images[position])

        Objects.requireNonNull(container).addView(itemView)
        return itemView
    }

}