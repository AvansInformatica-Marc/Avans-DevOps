package nl.marc.devops.board

import java.util.*

data class DateRange(val begin: Date, val end: Date) {
    infix fun Date.to(other: Date) = DateRange(begin, end)
}
