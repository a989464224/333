package cn.edu.sicnu.cs.stu.yuxingcai.a333

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecondActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_RESULT = "extra_result"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val input = findViewById<EditText>(R.id.et_input)
        findViewById<Button>(R.id.btn_confirm).setOnClickListener {
            val content = input.text.toString().trim()
            if (content.isEmpty()) {
                Toast.makeText(this, R.string.input_empty_toast, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val resultIntent = Intent().apply {
                putExtra(EXTRA_RESULT, content)
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
