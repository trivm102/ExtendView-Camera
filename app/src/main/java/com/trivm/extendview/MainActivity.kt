package com.trivm.extendview

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.trivm.extendview.camera.CameraHelper
import com.trivm.extendview.camera.extension.getImageBitmapRotated
import com.trivm.extendview.camera.extension.rotate
import com.trivm.extendview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var cameraHelper: CameraHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        cameraHelper = CameraHelper(
            owner = this,
            context = this.applicationContext,
            viewFinder = binding.cameraView,
            onResultImageCapture = ::onResultImageCapture,
            onFailure = ::onFailure
        )

        cameraHelper.start()

        binding.cameraView.post {
            Log.d("TRIVM", "post...")
            binding.textResult.visibility = View.VISIBLE
            binding.textResult.setOnClickListener {
                Log.d("TRIVM", "click...")
                cameraHelper.takeImage()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        cameraHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun onResultImageCapture(bitmap: Bitmap) {
        binding.resultImg.setImageBitmap(bitmap)
    }

    private fun onFailure(message: String?) {

    }

}