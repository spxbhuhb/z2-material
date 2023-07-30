package hu.simplexion.z2.browser.demo.table


import hu.simplexion.z2.browser.css.h100
import hu.simplexion.z2.browser.css.relative
import hu.simplexion.z2.browser.css.w100
import hu.simplexion.z2.browser.html.Z2
import hu.simplexion.z2.browser.html.grid
import hu.simplexion.z2.browser.html.gridTemplateColumns
import hu.simplexion.z2.browser.html.gridTemplateRows
import hu.simplexion.z2.browser.table.table

fun Z2.tableDemo() {

    class Row(
        val v1: String,
        val v2: Int
    )

    val data = (1..50).map {
        Row("s-$it", it)
    }

    grid(w100, h100, relative) {
        gridTemplateColumns = "minmax(0, 1fr)"
        gridTemplateRows = "minmax(0, 1fr)"

        table<Row> {
            rowId = { it.v1 }
            query = { data }

            column {
                label = "a"
                render = { text { it.v1 } }
            }

            column {
                label = "b"
                render = { text { it.v2 } }
            }

        }
    }


}