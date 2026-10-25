package cn.edu.sicnu.cs.stu.yuxingcai.a333

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class FourthActivity : AppCompatActivity() {

    private companion object {
        const val KEY_RUNNING = "key_running"
        const val KEY_START_TIME = "key_start_time"
        const val KEY_ELAPSED = "key_elapsed"
        const val UPDATE_INTERVAL = 1000L
    }

    private var running = false
    private var startTime = 0L
    private var elapsed = 0L

    private val handler = Handler(Looper.getMainLooper())
    private val updateRunnable = object : Runnable {
        override fun run() {
            updateTime()
            handler.postDelayed(this, UPDATE_INTERVAL)
        }
    }

    private lateinit var tvTime: TextView
    private lateinit var btnStart: Button
    private lateinit var btnPause: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_4)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvTime = findViewById(R.id.tv_time)
        btnStart = findViewById(R.id.btn_start)
        btnPause = findViewById(R.id.btn_pause)

        savedInstanceState?.let {
            running = it.getBoolean(KEY_RUNNING, false)
            startTime = it.getLong(KEY_START_TIME, 0L)
            elapsed = it.getLong(KEY_ELAPSED, 0L)
        }

        btnStart.setOnClickListener {
            startTime = SystemClock.elapsedRealtime()
            running = true
            updateButtons()
            handler.removeCallbacks(updateRunnable)
            handler.post(updateRunnable)
        }

        btnPause.setOnClickListener {
            elapsed += SystemClock.elapsedRealtime() - startTime
            running = false
            updateTime()
            updateButtons()
            handler.removeCallbacks(updateRunnable)
        }

        findViewById<Button>(R.id.btn_reset).setOnClickListener {
            elapsed = 0L
            startTime = SystemClock.elapsedRealtime()
            running = false
            updateTime()
            updateButtons()
            handler.removeCallbacks(updateRunnable)
        }

        updateButtons()
        updateTime()
    }

    private fun updateTime() {
        val total = if (running) elapsed + (SystemClock.elapsedRealtime() - startTime) else elapsed
        val totalSeconds = total / 1000
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60
        tvTime.text = getString(R.string.time_format, hours, minutes, seconds)
    }

    private fun updateButtons() {
        btnStart.isEnabled = !running
        btnPause.isEnabled = running
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_RUNNING, running)
        outState.putLong(KEY_START_TIME, startTime)
        outState.putLong(KEY_ELAPSED, elapsed)
    }

    override fun onResume() {
        super.onResume()
        if (running) {
            handler.removeCallbacks(updateRunnable)
            handler.post(updateRunnable)
        }
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(updateRunnable)
    }
}
