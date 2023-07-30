package hu.simplexion.z2.browser.table

import hu.simplexion.z2.browser.html.Z2

class TableColumnBuilder<T> {
    var label = ""
    var render : Z2.(row: T) -> Unit = { }
    var size = Double.NaN
    var exportable = true

    fun toColumn(table : Table<T>) : TableColumn<T> =
        TableColumn(table, label, render, size, exportable)

}