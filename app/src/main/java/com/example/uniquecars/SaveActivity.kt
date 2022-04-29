package com.example.uniquecars

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isInvisible
import com.example.uniquecars.databinding.ActivitySaveBinding


class SaveActivity : AppCompatActivity() {
    lateinit var uri: Uri
    lateinit var finalUri: Uri
    lateinit var imageUri: Uri
    lateinit var currentPhotoPath: String
    lateinit var year: String
    private lateinit var binding: ActivitySaveBinding
    private lateinit var bitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaveBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imageView.setImageResource(R.drawable.photo)
        bitmap = binding.imageView.drawable.toBitmap()
        binding.button.setOnClickListener {
            saveButton()
        }
        update()
    }

    private fun update() {
        val car = CarsSingleton.getArraylist()
        var key = intent.getIntExtra("key", 0)
        if (key == 1) {
            val imageBitmap = car?.image?.let { BitmapConverter.getImage(it) }
            var carname = car?.car_name
            var carmodel = car?.car_model
            var caryear = car?.car_year
            var carid = car?.id


            binding.imageView.setImageBitmap(imageBitmap)
            binding.carbrand.setText(carname)
            binding.carmodel.setText(carmodel)
            binding.carmodelyear.setText(caryear)
            binding.takephoto.isInvisible = true
            binding.button.text = "Düzenle"
            binding.button.setOnClickListener {
                var newname = binding.carbrand.text.toString()
                var newmodel = binding.carmodel.text.toString()
                var newyear = binding.carmodelyear.text.toString()
                val dao = CarsDAO(applicationContext)
                dao.update(newname, newmodel, newyear, carid!!)
                startActivity(Intent(this, MainActivity::class.java))
                Toast.makeText(this, "Düzenleme Yapıldı", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }


    fun saveButton() {
        val db = CarsDAO(this)
        if (binding.carbrand.text.isEmpty() || binding.carmodel.text.isEmpty() || binding.imageView.drawable == null) {
            Toast.makeText(this, "Boş bırakılan alanlar olamaz.", Toast.LENGTH_SHORT)
                .show()
        } else {
            val image = BitmapConverter.getBytes(bitmap)
            if (binding.carmodelyear.text.isEmpty()) {
                year = "1"
            } else {
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
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imagePath)
        startActivityForResult(intent, 1)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            binding.imageView.setImageURI(imageUri)
            bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
        }
    }

    private fun crateImage(): Uri {
        val resolver = contentResolver
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            uri = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        } else {
            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }
        val imgName = "${System.currentTimeMillis()}.jpg"
        val contentValues = ContentValues()
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, imgName)
        contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + "My Images/")
        finalUri = resolver.insert(uri, contentValues)!!
        imageUri = finalUri
        return finalUri
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}