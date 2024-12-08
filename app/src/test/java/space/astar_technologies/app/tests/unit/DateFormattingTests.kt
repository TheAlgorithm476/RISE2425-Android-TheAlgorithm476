package space.astar_technologies.app.tests.unit

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import org.junit.Assert
import org.junit.Test
import space.astar_technologies.app.ui.components.formatted
import kotlin.time.Duration

class DateFormattingTests {
    @Test
    fun `LocalDateTime -- Format to Astar style -- Past (-st suffix)`() {
        val date: LocalDateTime = LocalDateTime(2023, 2, 21, 16, 15)

        Assert.assertEquals("Tue, Feb 21st, 2023 at 4:15 PM UTC", date.formatted())
    }

    @Test
    fun `LocalDateTime -- Format to Astar style -- Past (-nd suffix)`() {
        val date: LocalDateTime = LocalDateTime(2023, 2, 22, 18, 30)

        Assert.assertEquals("Wed, Feb 22nd, 2023 at 6:30 PM UTC", date.formatted())
    }

    @Test
    fun `LocalDateTime -- Format to Astar style -- Past (-rd suffix)`() {
        val date: LocalDateTime = LocalDateTime(2023, 2, 23, 9, 0)

        Assert.assertEquals("Thu, Feb 23rd, 2023 at 9:00 AM UTC", date.formatted())
    }

    @Test
    fun `LocalDateTime -- Format to Astar style -- Past (-th suffix)`() {
        val date: LocalDateTime = LocalDateTime(2023, 2, 24, 9, 0)

        Assert.assertEquals("Fri, Feb 24th, 2023 at 9:00 AM UTC", date.formatted())
    }

    @Test
    fun `LocalDateTime -- Format to Astar style -- Future (-st suffix)`() {
        val date: LocalDateTime = LocalDateTime(2027, 6, 1, 16, 15)

        Assert.assertEquals("Tue, Jun 1st, 2027 at 4:15 PM UTC", date.formatted())
    }

    @Test
    fun `LocalDateTime -- Format to Astar style -- Future (-nd suffix)`() {
        val date: LocalDateTime = LocalDateTime(2027, 6, 2, 18, 30)

        Assert.assertEquals("Wed, Jun 2nd, 2027 at 6:30 PM UTC", date.formatted())
    }

    @Test
    fun `LocalDateTime -- Format to Astar style -- Future (-rd suffix)`() {
        val date: LocalDateTime = LocalDateTime(2027, 6, 3, 9, 0)

        Assert.assertEquals("Thu, Jun 3rd, 2027 at 9:00 AM UTC", date.formatted())
    }

    @Test
    fun `LocalDateTime -- Format to Astar style -- Future (-th suffix)`() {
        val date: LocalDateTime = LocalDateTime(2027, 6, 4, 9, 0)

        Assert.assertEquals("Fri, Jun 4th, 2027 at 9:00 AM UTC", date.formatted())
    }

    @Test
    fun `Duration -- Format Countdown -- Future -- Days, Hours, Minutes`() {
        val dateA: LocalDateTime = LocalDateTime(2024, 12, 4, 3, 0, 0)
        val dateB: LocalDateTime = LocalDateTime(2024, 12, 12, 7, 43, 2)

        val duration: Duration = dateA.toInstant(TimeZone.UTC).minus(dateB.toInstant(TimeZone.UTC))

        Assert.assertEquals("8 days, 4 hours, 43 minutes", duration.formatted())
    }

    @Test
    fun `Duration -- Format Countdown -- Future -- Days, Minutes`() {
        val dateA: LocalDateTime = LocalDateTime(2024, 12, 4, 3, 0, 0)
        val dateB: LocalDateTime = LocalDateTime(2024, 12, 12, 3, 43, 2)

        val duration: Duration = dateA.toInstant(TimeZone.UTC).minus(dateB.toInstant(TimeZone.UTC))

        Assert.assertEquals("8 days, 43 minutes", duration.formatted())
    }

    @Test
    fun `Duration -- Format Countdown -- Future -- Hours, Minutes`() {
        val dateA: LocalDateTime = LocalDateTime(2024, 12, 4, 3, 0, 0)
        val dateB: LocalDateTime = LocalDateTime(2024, 12, 4, 7, 43, 2)

        val duration: Duration = dateA.toInstant(TimeZone.UTC).minus(dateB.toInstant(TimeZone.UTC))

        Assert.assertEquals("4 hours, 43 minutes", duration.formatted())
    }

    @Test
    fun `Duration -- Format Countdown -- Future -- 1 Day`() {
        val dateA: LocalDateTime = LocalDateTime(2024, 12, 4, 3, 0, 0)
        val dateB: LocalDateTime = LocalDateTime(2024, 12, 5, 3, 0, 0)

        val duration: Duration = dateA.toInstant(TimeZone.UTC).minus(dateB.toInstant(TimeZone.UTC))

        Assert.assertEquals("1 day", duration.formatted())
    }

    @Test
    fun `Duration -- Format Countdown -- Future -- 1 Hour`() {
        val dateA: LocalDateTime = LocalDateTime(2024, 12, 4, 3, 0, 0)
        val dateB: LocalDateTime = LocalDateTime(2024, 12, 4, 2, 0, 0)

        val duration: Duration = dateA.toInstant(TimeZone.UTC).minus(dateB.toInstant(TimeZone.UTC))

        Assert.assertEquals("1 hour", duration.formatted())
    }

    @Test
    fun `Duration -- Format Countdown -- Future -- 1 Minute`() {
        val dateA: LocalDateTime = LocalDateTime(2024, 12, 4, 3, 0, 0)
        val dateB: LocalDateTime = LocalDateTime(2024, 12, 4, 2, 59, 0)

        val duration: Duration = dateA.toInstant(TimeZone.UTC).minus(dateB.toInstant(TimeZone.UTC))

        Assert.assertEquals("1 minute", duration.formatted())
    }
}