package com.example.androiddevchallenge

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun TimerScreen(
    selectedTime: TimeSetting,
    timerCount: Long,
    onTimeSelected: (TimeSetting) -> Unit,
    onStartTimer: (TimeSetting) -> Unit
) {
    Log.d("aaa", "TimerScreen Build :selected=${selectedTime}")
    Log.d("aaa", "TimerScreen Build :count=${timerCount}")
    Column {
        TimerLayout(timerCount)
        SettingsLayout(
            selectedTime = selectedTime,
            onTimeSelected = onTimeSelected
        )
        StartButton(
            selectedTime = selectedTime,
            onStartTimer = onStartTimer
        )
    }
}

@Composable
fun TimerLayout(
    timerCount: Long
) {
    Text(text = timerCount.toString())
}

@Composable
fun SettingsLayout(
    selectedTime: TimeSetting,
    onTimeSelected: (TimeSetting) -> Unit
) {
    Row {
        RadioButton(selected = selectedTime == TimeSetting.TIME_5, onClick = { onTimeSelected(TimeSetting.TIME_5) })
        Spacer(modifier = Modifier.size(4.dp))
        RadioButton(selected = selectedTime == TimeSetting.TIME_10, onClick = { onTimeSelected(TimeSetting.TIME_10) })
        Spacer(modifier = Modifier.size(4.dp))
        RadioButton(selected = selectedTime == TimeSetting.TIME_30, onClick = { onTimeSelected(TimeSetting.TIME_30) })
        Spacer(modifier = Modifier.size(4.dp))
        RadioButton(selected = selectedTime == TimeSetting.TIME_60, onClick = { onTimeSelected(TimeSetting.TIME_60) })
        Spacer(modifier = Modifier.size(4.dp))
    }
}


@Composable
fun StartButton(
    selectedTime: TimeSetting,
    onStartTimer: (TimeSetting) -> Unit
) {
    Button(onClick = { onStartTimer(selectedTime) }) {
        Text(text = "start")
    }
}