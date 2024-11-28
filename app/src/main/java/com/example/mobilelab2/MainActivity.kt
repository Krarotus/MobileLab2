package com.example.mobilelab2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.os.SystemClock
import  android.widget.Button
import android.widget.Chronometer

class MainActivity : AppCompatActivity() {

    lateinit var stopwatch: Chronometer
    var running = false
    var offset: Long = 0

    val OFFSET_KEY = "offset"
    val RUNNING_KEY = "running"
    val BASE_KEY = "base"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Получение ссылки на секундомер
        stopwatch = findViewById<Chronometer>(R.id.stopwatch)

        //Восстановление предыдущего состояния
        if (SavedInstanceState !=null) {
            offset = savedInstanceState.getLong(OFFSET_KEY)
            running = savedInstanceState.getBoolean(RUNNING_KEY)
            if(running){
                stopwatch.base = savedInstanceState.getLong(BASE_KEY)
                stopwatch.start()
            }else setBaseTime()

        }



        val startButton = findViewById<Button>(R.id.start_button)
        startButton.setOnClickListener{
            if(!running){
                setBaseTime()
                stopwatch.start()
                running=true
            }
        }

        val pauseButton = findViewById<Button>(R.id.pause_button)
        pauseButton.setOnClickListener{
            if (running){
                saveOffset()
                stopwatch.stop()
                running = false
            }
        }



        val resetButton = findViewById<Button>(R.id.reset_button)
        resetButton.setOnClickListener{
            offset = 0
            setBaseTime()
        }

        fun onSaveInstanceState(savedInstanceState: Bundle) {
            savedInstanceState.putLong(OFFSET_KEY, offset)
            savedInstanceState.putBoolean(RUNNING_KEY, running)
            savedInstanceState.putLong(BASE_KEY, stopwatch.base)
            super.onSaveInstanceState(savedInstanceState)
        }


        fun setBaseTime(){
            stopwatch.base = SystemClock.elapsedRealtime() - offset
        }
        fun saveOffset(){
            offset = SystemClock.elapsedRealtime() - stopwatch.base
        }




    }
}