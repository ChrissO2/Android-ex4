package com.example.exercise4.images

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Size
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.exercise4.R
import java.io.FileNotFoundException
import java.io.InputStream
import com.example.exercise4.databinding.ImageItemBinding

class ImageListAdapter(private val appContext: Context, private var dList: MutableList<ImageItem>, private val fragment: Fragment):
    RecyclerView.Adapter<ImageListAdapter.MyViewHolder>() {
    inner class MyViewHolder(viewBinding: ImageItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        val img = viewBinding.image
    }
    init {
        Log.d("myTag", "ImageListAdapter: ${dList.size}")
    }

    companion object {
        const val MAX_PAGE_COUNT = 100
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageListAdapter.MyViewHolder {
        val viewBinding = ImageItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return MyViewHolder(viewBinding)
    }

    override fun onBindViewHolder(vHolder: ImageListAdapter.MyViewHolder, position: Int) {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            dList[position].curi?.let {
                vHolder.img.setImageBitmap(
                    appContext.contentResolver.loadThumbnail(
                        it,
                        Size(150, 150),
                        null
                    )
                )
            }
        } else
            vHolder.img.setImageBitmap(getBitmapFromUri(appContext, dList[position].curi))

        vHolder.img.setOnClickListener{_ ->
            val navController = findNavController(fragment)
            val bundle = Bundle()
            bundle.putInt("position", position)
            navController.navigate(R.id.imageOptionsFragment, bundle)
        }
    }

    override fun getItemCount(): Int {
        return Math.min(MAX_PAGE_COUNT, dList.size)
    }

    fun getBitmapFromUri(mContext: Context, uri: Uri?): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val image_stream: InputStream
            try {
                image_stream = uri?.let {
                    mContext.getContentResolver().openInputStream(it)
                }!!
                bitmap = BitmapFactory.decodeStream(image_stream)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bitmap
    }
}