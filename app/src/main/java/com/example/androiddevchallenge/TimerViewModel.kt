/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.content.Context
import android.os.CountDownTimer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlin.math.ceil

class TimerViewModel : ViewModel() {
    enum class State {
        BEFORE_START,
        PAUSE,
        STARTED
    }

    // States
    var currentState by mutableStateOf(State.BEFORE_START)
    val isStarted: Boolean
        get() = currentState == State.STARTED
    var currentTimeMillis: Long by mutableStateOf(TimeSetting.TIME_10.time.toMillis())
    val currentTimeSeconds: Int
        get() = ceil(currentTimeMillis / 1000f).toInt()
    var selectedTimeSetting by mutableStateOf(TimeSetting.TIME_10)

    // Execution instances
    private var timer: CountDownTimer? = null
    private lateinit var soundPlayer: SoundPlayer

    fun prepare(context: Context) {
        soundPlayer = SoundPlayer(context)
    }

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
                soundPlayer.playBuzzer()
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
