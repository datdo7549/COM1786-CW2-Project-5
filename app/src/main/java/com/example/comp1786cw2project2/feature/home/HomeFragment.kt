package com.example.comp1786cw2project2.feature.home

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.webkit.URLUtil
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.com1786_cw2project1.R
import com.example.com1786_cw2project1.databinding.FragmentHomeBinding
import com.example.comp1786cw2project2.feature.CustomInputDialog
import com.example.comp1786cw2project2.feature.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.io.File


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    override val viewModel: HomeViewModel by viewModels()

    override fun onCreateViewBinding(inflater: LayoutInflater): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
    }

    private fun initView() = with(viewBinding) {
        edtImageUrl.setOnClickListener {
            val customInputDialog = CustomInputDialog(requireContext(), object : CustomInputDialog.OnInputListener {
                override fun onInput(text: String) {
                    val url = text
                    if (URLUtil.isValidUrl(url)) {
                        viewModel.storeImageToLocalDatabase(url)
                    } else {
                        Log.d("TAG", "Invalid url!")
                    }
                }
            })

            customInputDialog.show(childFragmentManager, "ad")
        }

        btnCamera.setOnClickListener {
            dispatchTakePictureIntent()
        }

        btnPrevious.setOnClickListener {
            viewModel.onPreviousClicked()
        }

        btnNext.setOnClickListener {
            viewModel.onNextClicked()
        }
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivityForResult(takePictureIntent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            saveThisImageToDatabase(imageBitmap)
        }
    }

    private fun saveThisImageToDatabase(bitmap: Bitmap) {
        val displayName = "picture_saved.jpg"
        val mimeType = "image/jpeg"
        val collection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, displayName)
            put(MediaStore.Images.Media.MIME_TYPE, mimeType)
            put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        }
        val contentResolver = context?.contentResolver
        val imageUri = contentResolver?.insert(collection, values)
        contentResolver?.openOutputStream(imageUri!!).use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        }

        imageUri?.let {
            val filePath: String = pathImage(it)
            viewModel.storeImageToLocalDatabase(filePath)
        }
    }

    private fun observeData() = with(viewModel) {
        currentUrl.observe(viewLifecycleOwner) {
            if (it.url.isNotEmpty() && URLUtil.isValidUrl(it.url)) {
                Glide
                    .with(requireActivity())
                    .load(it.url)
                    .centerCrop()
                    .error(R.drawable.error)
                    .into(viewBinding.imvShowImage)
            } else {
                val finalFile = File(it.url)
                if (finalFile.exists()) {
                    val bitmap = BitmapFactory.decodeFile(finalFile.absolutePath)
                    viewBinding.imvShowImage.setImageBitmap(bitmap)
                }
            }
        }
    }

    private fun initViewModel() = with(viewModel) {
        getData()
        observeData()
    }

    private fun getData() = with(viewModel) {
        getCurrentUrlImageFromDatabase(0)
    }

    private fun pathImage(uri: Uri): String {
        arrayOf(MediaStore.Images.Media.DATA)
        val cursor = requireContext().contentResolver.query(uri, arrayOf(MediaStore.Images.Media.DATA), null, null, null)
        val columnIndex = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val filePath = cursor.getString(columnIndex)
        cursor.close()
        return filePath
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}