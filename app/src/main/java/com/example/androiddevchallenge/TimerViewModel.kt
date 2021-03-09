package com.example.androiddevchallenge

import android.os.CountDownTimer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class TimerViewModel : ViewModel() {
    enum class State {
        BEFORE_START,
        PAUSE,
        STARTED
    }

    var currentState by mutableStateOf(State.BEFORE_START)
    val isStarted: Boolean
        get() = currentState == State.STARTED
    var currentTimeMillis: Long by mutableStateOf(TimeSetting.TIME_10.time.toMillis())
    val currentTimeSeconds: Int
        get() = (currentTimeMillis / 1000).toInt()
    var selectedTimeSetting by mutableStateOf(TimeSetting.TIME_10)

    private var timer: CountDownTimer? = null

    fun toggleTimer(setting: TimeSetting) {
        if (isStarted) {
            stopTimer()
        } else {
            startTimer(setting)
        }
    }

    private fun startTimer(setting: TimeSetting) {
        val startTime = when (currentState) {
            State.BEFORE_START -> setting.time.toMillis()
            State.PAUSE, State.STARTED -> currentTimeMillis
        }

        timer = object : CountDownTimer(startTime, 30L) {
            override fun onTick(millisUntilFinished: Long) {
                currentTimeMillis = millisUntilFinished
            }

            override fun onFinish() {
                currentState = State.BEFORE_START
                currentTimeMillis = 0
            }

        }
        timer?.start()
        currentState = State.STARTED
    }

    private fun stopTimer() {
        timer?.cancel()
        currentState = State.PAUSE
    }

    fun onSelectTime(timeSetting: TimeSetting) {
        selectedTimeSetting = timeSetting
        currentTimeMillis = timeSetting.time.toMillis()
    }

}