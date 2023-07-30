package hu.simplexion.z2.browser.demo.table


import hu.simplexion.z2.browser.html.Z2
import hu.simplexion.z2.browser.layout.container
import hu.simplexion.z2.browser.table.table

fun Z2.tableDemo() {

    class Row(
        val v1: String,
        val v2: Int
    )

    val data = (1..50).map {
        Row("s-$it", it)
    }

    container("p0", scroll = false) {
        table<Row> {
            rowId = { it.v1 }
            query = { data }

            column {
                label = "Header A"
                render = { text { it.v1 } }
                comparator = { a, b -> a.v2.compareTo(b.v2) }
            }

            column {
                label = "Header B"
                render = { text { it.v2 } }
                comparator = { a, b -> a.v2.compareTo(b.v2) }
            }

        }
    }


}