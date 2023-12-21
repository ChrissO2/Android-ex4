package com.example.exercise4.images

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.FileProvider
import com.example.exercise4.BuildConfig
import java.io.File

class ImageRepository {
    lateinit var uri: Uri
    
    fun getSharedList(): MutableList<ImageItem>? {
        sharedStoreList?.clear()
        
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        
        val contentResolver: ContentResolver = ctx.contentResolver
        val cursor = contentResolver.query(uri, null, null, null, null)
        if (cursor == null) {
        } else if (!cursor.moveToFirst()) {
        } else {
            val idColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID)
            val nameColumn = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
            do {
                var thisId = cursor.getLong(idColumn)
                var thisName = cursor.getString(nameColumn)
                var thisContentUri = ContentUris.withAppendedId(uri, thisId)
                var thisUriPath = thisContentUri.toString()
                sharedStoreList?.add(
                    ImageItem(
                        thisName,
                        thisUriPath,
                        "No path yet",
                        thisContentUri
                    )
                )
            } while (cursor.moveToNext())
            cursor.close()
        }
        return sharedStoreList
    }

    fun getAppList(): MutableList<ImageItem>? {
        val dir: File? =
            ctx.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        dir?.listFiles()
        appStoreList?.clear()
        if (dir?.isDirectory == true) {
            var fileList = dir.listFiles()
            if (fileList != null) {
                for (value in fileList) {
                    var fileName =
                        value.name
                    if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(
                            ".png"
                        )
                    ) {
                        val tmpUri = FileProvider.getUriForFile(
                            ctx,
                            "${BuildConfig.APPLICATION_ID}.provider",
                            value
                        )
                        appStoreList?.add(
                            ImageItem(
                                fileName,
                                value.toURI().path,
                                value.absolutePath,
                                tmpUri
                            )
                        )
                    }
                }
            }
        }
        val size = appStoreList?.size
        return appStoreList
    }

    fun addPhotoItem(item: ImageItem) {
        if (photo_storage == SHARED_S) {
            sharedStoreList?.add(item)
        } else {
            appStoreList?.add(item)
        }
    }

    fun setStorage(storage: Int): Boolean {
        if (storage != SHARED_S && storage != PRIVATE_S)
            return false
        photo_storage = storage
        return true
    }

    companion object {
        private var INSTANCE: ImageRepository? = null
        private lateinit var ctx: Context
        const val SHARED_S = 1
        const val PRIVATE_S = 2
        var sharedStoreList: MutableList<ImageItem>? = null
        var appStoreList: MutableList<ImageItem>? = null

        fun getinstance(ctx: Context): ImageRepository {
            if (INSTANCE == null) {
                INSTANCE = ImageRepository()
                sharedStoreList = mutableListOf<ImageItem>()
                appStoreList = mutableListOf<ImageItem>()
                this.ctx = ctx
            }
            return INSTANCE as ImageRepository
        }

        var photo_storage = SHARED_S

        fun getStorage(): Int {
            return photo_storage
        }
    }
}