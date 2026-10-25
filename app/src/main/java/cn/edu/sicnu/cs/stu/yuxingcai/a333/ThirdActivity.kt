package cn.edu.sicnu.cs.stu.yuxingcai.a333

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.BundleCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ThirdActivity : AppCompatActivity() {

    private companion object {
        // MediaStore 拍照返回缩略图的 extra 键名
        const val KEY_DATA = "data"
    }

    private lateinit var photoView: ImageView

    private val takePictureLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val extras = result.data?.extras
                val bitmap = extras?.let { BundleCompat.getParcelable(it, KEY_DATA, Bitmap::class.java) }
                if (bitmap != null) {
                    photoView.setImageBitmap(bitmap)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_3)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        photoView = findViewById(R.id.iv_photo)

        findViewById<Button>(R.id.btn_take_photo).setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (intent.resolveActivity(packageManager) != null) {
                takePictureLauncher.launch(intent)
            } else {
                Toast.makeText(this, R.string.no_camera, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
