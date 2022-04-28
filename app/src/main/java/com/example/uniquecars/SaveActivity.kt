package com.example.uniquecars

import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.uniquecars.databinding.ActivitySaveBinding
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.net.URI


class SaveActivity : AppCompatActivity() {
    lateinit var uri: Uri
    lateinit var finalUri :Uri
    lateinit var imageUri: Uri
    lateinit var currentPhotoPath: String
    lateinit var year:String
    private lateinit var binding: ActivitySaveBinding
    private lateinit var bitmap: Bitmap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaveBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imageView.setImageResource(R.drawable.photo)
        binding.button.setOnClickListener {
            saveButton(bitmap)
        }
    }


    fun saveButton(bitmap: Bitmap) {
        val image = BitmapConverter.getBytes(bitmap)
        val db = CarsDAO(this)
        if(binding.carbrand.text.isEmpty() || binding.carmodel.text.isEmpty()){
            Toast.makeText(this,"Araç adı ve ya modelini boş bıraktınız.",Toast.LENGTH_SHORT).show()
        }else{
            if (binding.carmodelyear.text.isEmpty()){
                year= "1"
            }else{
                year = binding.carmodelyear.text.toString()
            }
            db.insertDB(
                binding.carbrand.text.toString(),
                binding.carmodel.text.toString(),
                year,
                image!!
            )
            Toast.makeText(this, "Kayıt Yapıldı", Toast.LENGTH_LONG).show()
            startActivity(Intent(application, MainActivity::class.java))
            finish()
        }

    }

    fun takePhoto(view: View) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val imagePath = crateImage()
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imagePath)
        startActivityForResult(intent, 1)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            binding.imageView.setImageURI(imageUri)
            bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,imageUri)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun crateImage():Uri{
        val resolver = contentResolver
        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.Q){
            uri = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        }else{
            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }
        val imgName = "${System.currentTimeMillis()}.jpg"
        val contentValues = ContentValues()
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, imgName)
        contentValues.put(MediaStore.Images.Media.RELATIVE_PATH,"Pictures/"+"My Images/")
        finalUri = resolver.insert(uri,contentValues)!!
        imageUri = finalUri
        return finalUri
    }
}