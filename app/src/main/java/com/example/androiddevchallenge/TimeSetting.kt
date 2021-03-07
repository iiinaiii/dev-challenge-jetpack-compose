package com.example.androiddevchallenge

import java.time.Duration

enum class TimeSetting(val time: Duration) {
    TIME_60(Duration.ofSeconds(60)),
    TIME_30(Duration.ofSeconds(30)),
    TIME_10(Duration.ofSeconds(10)),
    TIME_5(Duration.ofSeconds(5))
}