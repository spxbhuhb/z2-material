package hu.simplexion.z2.browser.table

class TableBuilder<T> : TableConfiguration() {

    val columns = mutableListOf<TableColumnBuilder<T>>()

    var query: (() -> List<T>)? = null

    var rowId: ((row : T) -> String)? = null

    fun column(builder : TableColumnBuilder<T>.() -> Unit) {
        columns += TableColumnBuilder<T>().apply { builder () }
    }

}