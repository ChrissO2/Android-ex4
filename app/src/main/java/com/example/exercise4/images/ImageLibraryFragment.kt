package com.example.exercise4.images

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.exercise4.R
import com.example.exercise4.databinding.FragmentImageLibraryBinding
import com.example.exercise4.images.ImageRepository.Companion.PRIVATE_S
import com.example.exercise4.images.ImageRepository.Companion.SHARED_S
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import android.content.ActivityNotFoundException
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.exercise4.BuildConfig


class ImageLibraryFragment : Fragment() {
    private lateinit var binding: FragmentImageLibraryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding  = FragmentImageLibraryBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recView = binding.gridView
        val dataRepo = ImageRepository.getinstance(requireContext())
        dataRepo.setStorage(PRIVATE_S)
        var adapter = dataRepo.getAppList()?.let {ImageListAdapter(requireContext(), it, this)}
        if (adapter == null) {
            Toast.makeText(
                requireContext(), "Invalid Data",
                Toast.LENGTH_LONG
            ).show()
            requireActivity().onBackPressed()
        }
        recView.layoutManager = GridLayoutManager(requireContext(), 3)
        recView.adapter = adapter

        val photoLauncher =
            registerForActivityResult(ActivityResultContracts.TakePicture()) { result: Boolean ->
                if (result) {
                    Toast.makeText(requireContext(), "Photo TAKEN", Toast.LENGTH_LONG).show()
                    adapter = dataRepo.getAppList()
                        ?.let { ImageListAdapter(requireContext(), it, this) }
                    recView.adapter = adapter
                } else {
                    Toast.makeText(requireContext(), "Photo NOT taken!", Toast.LENGTH_LONG).show()
                }
            }

        binding.button.setOnClickListener {
            try {
                photoLauncher.launch(getNewFileUri())
                ImageListAdapter(requireContext(), dataRepo.getAppList()!!, this).notifyDataSetChanged()
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(requireContext(), "CAMERA DOESN'T WORK!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getNewFileUri(): Uri {
        val dir: File
        when (ImageRepository.getStorage()) {
            SHARED_S -> dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            PRIVATE_S -> dir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
            else -> return MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }

        val tStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val tmpFile = File.createTempFile(
            "Photo_" + "${tStamp}",
            ".jpg",
            dir
        )
        return FileProvider.getUriForFile(
            requireContext(),
            "${BuildConfig.APPLICATION_ID}.provider",
            tmpFile
        )
    }
}