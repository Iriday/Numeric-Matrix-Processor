package processor

import java.math.BigDecimal

fun isMatricesSizesEqual(a: Array<Array<BigDecimal>>, b: Array<Array<BigDecimal>>): Boolean {
    if (a.size != b.size) return false
    val firstRowSize = a[0].size
    a.forEachIndexed { i, row -> if (row.size != firstRowSize || b[i].size != firstRowSize) return false }
    return true
}
