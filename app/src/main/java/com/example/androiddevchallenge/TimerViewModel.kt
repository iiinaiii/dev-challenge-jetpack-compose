package com.example.androiddevchallenge

import android.os.CountDownTimer
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class TimerViewModel : ViewModel() {
    var currentTime by mutableStateOf(0L)
    var selected by mutableStateOf(TimeSetting.TIME_10)

    private var timer: CountDownTimer? = null

    fun startTimer(setting: TimeSetting) {
        Log.d("aaa", "startTimer ")
        timer = object : CountDownTimer(setting.time.toMillis(), 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                Log.d("aaa", "onTick :$millisUntilFinished")
                currentTime = millisUntilFinished
            }

            override fun onFinish() {
                Log.d("aaa", "onFinish!!!!! ")
            }

        }
        timer?.start()
    }

    fun onSelectTime(timeSetting: TimeSetting) {
        Log.d("aaa", "onSelectTime!!!!! ")
        selected = timeSetting
        currentTime = timeSetting.time.toMillis()
    }

    fun stopTimer() {}

}