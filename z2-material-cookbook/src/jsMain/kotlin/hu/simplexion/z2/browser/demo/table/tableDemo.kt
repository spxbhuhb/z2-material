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

    container("padding-0", scroll = false) {
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