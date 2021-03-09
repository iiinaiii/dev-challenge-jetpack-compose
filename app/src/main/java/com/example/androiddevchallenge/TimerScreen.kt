package com.example.androiddevchallenge

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.ui.theme.*


@Composable
fun TimerScreen(
    selectedTime: TimeSetting,
    timerCount: Long,
    timerCountSeconds: Int,
    onTimeSelected: (TimeSetting) -> Unit,
    onStartTimer: (TimeSetting) -> Unit,
    isStared: Boolean
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(100.dp))
        TimerLayout(timerCount, timerCountSeconds)
        Spacer(modifier = Modifier.height(64.dp))
        SettingsLayout(
            selectedTime = selectedTime,
            onTimeSelected = onTimeSelected
        )
        Spacer(modifier = Modifier.height(16.dp))
        StartButton(
            isStared = isStared,
            selectedTime = selectedTime,
            onStartTimer = onStartTimer
        )
    }
}

@Composable
fun TimerLayout(
    timerCountMillis: Long,
    timerCountSeconds: Int
) {
    val ratio: Float = timerCountMillis % 1000 / 1000f
    val sweepAngle = 360 * (1 - ratio)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorTimerBackground)
    ) {
        //Background
        Canvas(
            modifier = Modifier
                .clipToBounds()
                .fillMaxWidth()
                .height(240.dp)
                .align(Alignment.Center)
        ) {
            drawLine(
                color = colorTimerBackgroundLine,
                start = Offset(size.width / 2f, 0f),
                end = Offset(size.width / 2f, size.height),
                strokeWidth = 18f
            )
            drawLine(
                color = colorTimerBackgroundLine,
                start = Offset(0f, size.height / 2f),
                end = Offset(size.width, size.height / 2f),
                strokeWidth = 18f
            )
            // Outer circle
            drawCircle(
                color = colorTimerBackgroundCircle,
                style = Stroke(width = 16f),
                radius = size.minDimension / 2.2f
            )
            // Inner circle
            drawCircle(
                color = colorTimerBackgroundCircle,
                style = Stroke(width = 12f),
                radius = size.minDimension / 2.6f
            )
            // Overlay
            drawArc(
                color = colorTimerBackgroundOverlay,
                style = Fill,
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = true,
                size = Size(width = 1400f, height = 1400f),
                topLeft = Offset(-160f, -370f)
            )

        }
        // Countdown number
        Text(
            text = timerCountSeconds.toString(),
            color = colorTimerNumber,
            fontSize = 88.sp,
            fontFamily = FontFamily.SansSerif,
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}

@Composable
fun SettingsLayout(
    selectedTime: TimeSetting,
    onTimeSelected: (TimeSetting) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Spacer(modifier = Modifier.size(4.dp))
        TextRadioButton(
            text = "5",
            selected = selectedTime == TimeSetting.TIME_5,
            onClick = { onTimeSelected(TimeSetting.TIME_5) }
        )
        TextRadioButton(
            text = "10",
            selected = selectedTime == TimeSetting.TIME_10,
            onClick = { onTimeSelected(TimeSetting.TIME_10) }
        )
        TextRadioButton(
            text = "30",
            selected = selectedTime == TimeSetting.TIME_30,
            onClick = { onTimeSelected(TimeSetting.TIME_30) }
        )
        TextRadioButton(
            text = "60",
            selected = selectedTime == TimeSetting.TIME_60,
            onClick = { onTimeSelected(TimeSetting.TIME_60) }
        )
        Spacer(modifier = Modifier.size(4.dp))
    }
}

@Composable
fun TextRadioButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = text,
            fontSize = 24.sp
        )
        RadioButton(
            selected = selected,
            onClick = onClick
        )
    }
}


@Composable
fun StartButton(
    isStared: Boolean,
    selectedTime: TimeSetting,
    onStartTimer: (TimeSetting) -> Unit
) {
    val imageResource = if (isStared) {
        painterResource(id = R.drawable.ic_pause)
    } else {
        painterResource(id = R.drawable.ic_play)
    }

    Button(
        onClick = { onStartTimer(selectedTime) },
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
    ) {
        Image(painter = imageResource, contentDescription = null)
    }
}

@Preview("TimerScreen", widthDp = 360, heightDp = 640)
@Composable
fun TimerScreenPreview() {
    TimerScreen(
        selectedTime = TimeSetting.TIME_30,
        timerCount = 1000,
        timerCountSeconds = 1,
        onStartTimer = {},
        onTimeSelected = {},
        isStared = false
    )
}
