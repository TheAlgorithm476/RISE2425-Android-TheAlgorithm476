package space.astar_technologies.app.ui.components

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinTimeZone
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.time.Duration
import java.util.TimeZone as JavaTimeZone

private fun getDateSuffix(day: Int): String = when (day) {
    1, 21, 31 -> "st"
    2, 22 -> "nd"
    3, 23 -> "rd"
    else -> "th"
}

// NO. JUST NO. WHY WOULD YOU EVEN THINK OF THAT
@VisibleForTesting
internal fun LocalDateTime.formatted(): String {
    val date: LocalDateTime = this.toInstant(TimeZone.UTC).toLocalDateTime(TimeZone.UTC)

    val monthDayFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("EE, MMM d")
    val yearFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(", yyyy")
    val hourFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("h:mm a")

    return "${monthDayFormatter.format(this)}${getDateSuffix(this.dayOfMonth)}${yearFormatter.format(this)} at ${hourFormatter.format(date.toJavaLocalDateTime())} UTC"
}

@VisibleForTesting
internal fun Duration.formatted(): String = when (this.inWholeMinutes) {
    0L -> "now"
    else -> buildString {
        absoluteValue.toComponents { days: Long, hours: Int, minutes: Int, _, _ ->
            val hasDays: Boolean = days != 0L
            val hasHours: Boolean = hours != 0
            val hasMinutes: Boolean = minutes != 0
            var components: Int = 0

            if (hasDays) {
                this.append(days).append(" day")
                if (days != 1L) this.append('s')
                components++
            }

            if (hasHours) {
                if (components++ > 0) this.append(", ")
                this.append(hours).append(" hour")
                if (hours != 1) this.append('s')
            }

            if (hasMinutes) {
                if (components > 0) this.append(", ")
                this.append(minutes).append(" minute")
                if (minutes != 1) this.append('s')
            }
        }
    }
}

private fun DateTimeFormatter.format(date: LocalDateTime): String = format(date.toJavaLocalDateTime())

@Composable
fun DateText(date: LocalDateTime?, isNET: Boolean?, modifier: Modifier = Modifier) {
    if (date == null) {
        Text(
            text = "Launch Date TBD",
            fontStyle = FontStyle.Italic,
            modifier = modifier
        )
        return
    }

    Text(
        text = "${if (isNET == true) "No Earlier Than " else ""}${date.formatted()}",
        fontStyle = FontStyle.Italic,
        modifier = modifier
    )
}

@Composable
fun TimeToLaunchText(date: LocalDateTime?, isNET: Boolean? = null) {
    if (isNET == null || isNET) return
    if (date == null) return

    val timezone: TimeZone = JavaTimeZone.getDefault().toZoneId().toKotlinTimeZone()
    val currentTime: LocalDateTime = Clock.System.now()
        .toLocalDateTime(timezone)
    val difference: Duration = date.toInstant(timezone).minus(currentTime.toInstant(timezone))

    if (difference.isNegative()) return

    Text(
        text = difference.formatted(),
        fontSize = 14.sp
    )
}

@Preview
@Composable
fun DateText_Preview() {
    Column {
        DateText(LocalDateTime.parse("2024-09-28T13:00:00"), false)
        DateText(null, true)
        DateText(LocalDateTime.parse("2024-09-28T13:00:00"), true)
    }
}

@Preview
@Composable
fun TimeToLaunchText_Preview() {
    Column {
        TimeToLaunchText(LocalDateTime.parse("2024-11-28T13:00:00"), false)
        TimeToLaunchText(LocalDateTime.parse("2024-09-28T13:00:00"), true)
        TimeToLaunchText(LocalDateTime.parse("2024-09-28T13:00:00"))
        TimeToLaunchText(null)
    }
}