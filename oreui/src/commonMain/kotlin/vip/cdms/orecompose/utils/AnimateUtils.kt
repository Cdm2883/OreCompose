package vip.cdms.orecompose.utils

import androidx.compose.animation.core.RepeatMode
import androidx.compose.runtime.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration

fun tickerFlow(period: Duration, initialDelay: Duration = Duration.ZERO) = flow {
    delay(initialDelay)
    while (true) {
        emit(Unit)
        delay(period)
    }
}

@Composable
fun tickFloat(
    begin: Float,
    end: Float,
    step: Float,
    ticker: () -> Flow<*>,
    repeatMode: RepeatMode = RepeatMode.Reverse
): MutableFloatState {
    require(end > begin)
    val valuer = remember { mutableFloatStateOf(begin) }
    var value by valuer
    LaunchedEffect(Unit) {
        var addition = step
        val flow = ticker()
        flow.collect {
            value += addition
            when (repeatMode) {
                RepeatMode.Restart -> if (value > end) value = begin else if (value < begin) value = end
                RepeatMode.Reverse -> if (value >= end || value <= begin) addition *= -1
            }
        }
    }
    return valuer
}
