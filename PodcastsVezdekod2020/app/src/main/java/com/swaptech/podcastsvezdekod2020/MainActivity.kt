package com.swaptech.podcastsvezdekod2020

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var permissions = arrayOf(READ_EXTERNAL_STORAGE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageButton.setOnClickListener{
            //check  runtime permission
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

                    requestPermissions(permissions, PERMISSION_CODE)
                }
                else {
                    pickImageFromGallery()
                }
            }
            else {
                pickImageFromGallery()
            }
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    companion object{
        //image pick code
        private val IMAGE_PICK_CODE = 1000

        
        //permission code
        private val PERMISSION_CODE =1001
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
    {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                        //permission from popup granted
                        pickImageFromGallery()
                    }
            else{
                //permission from popup denied
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show() }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {


        if(resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            imageButton.setImageURI(data?.data)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}