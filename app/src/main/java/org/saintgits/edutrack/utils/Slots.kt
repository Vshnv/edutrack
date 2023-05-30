package org.saintgits.edutrack.utils

private val slotTimes = listOf(
"09:00AM - 09:50AM",
"09:50AM - 10:40AM",
"10:50AM - 11:40AM",
"11:40AM - 12:30PM",
"01:30PM - 02:20PM",
"02:30PM - 03:10PM",
"03:20PM - 04:10PM"
)

fun nthSlotTime(n: Int): String {
    return slotTimes[n % 7]
}