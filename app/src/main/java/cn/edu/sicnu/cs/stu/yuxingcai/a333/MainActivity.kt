package cn.edu.sicnu.cs.stu.yuxingcai.a333

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var resultText: TextView

    private val secondActivityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val content = result.data?.getStringExtra(SecondActivity.EXTRA_RESULT)
                if (!content.isNullOrEmpty()) {
                    resultText.text = getString(R.string.result_format, content)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        resultText = findViewById(R.id.tv_result)

        findViewById<Button>(R.id.btn_pass_param).setOnClickListener {
            val intent = Intent(this, FirstActivity::class.java).apply {
                putExtra(FirstActivity.EXTRA_PARAM, getString(R.string.pass_param_value))
            }
            startActivity(intent)
        }

        findViewById<Button>(R.id.btn_return_result).setOnClickListener {
            secondActivityLauncher.launch(Intent(this, SecondActivity::class.java))
        }

        findViewById<Button>(R.id.btn_camera).setOnClickListener {
            startActivity(Intent(this, ThirdActivity::class.java))
        }

        findViewById<Button>(R.id.btn_stopwatch).setOnClickListener {
            startActivity(Intent(this, FourthActivity::class.java))
        }
    }
}
