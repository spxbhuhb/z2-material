/*
 * Copyright © 2020-2021, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
package hu.simplexion.z2.browser.table

import hu.simplexion.z2.browser.html.*
import hu.simplexion.z2.browser.material.px
import hu.simplexion.z2.browser.util.uniqueNodeId
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.dom.addClass
import kotlinx.dom.removeClass
import org.w3c.dom.HTMLElement
import org.w3c.dom.events.Event
import org.w3c.dom.events.MouseEvent
import kotlin.math.max


abstract class TableColumn<T>(
    val table: Table<T>
) {
    val id = uniqueNodeId

    val element = document.createElement("th") as HTMLElement

    open val min = 24.0
    open var max = "1fr"

    open var size = Double.NaN
    open var exportable = true

    open var label: String = ""
    lateinit var sortSign: Z2

    var beingResized = false
    var beenResized = false
    var lastX: Int = 0

    var sortAscending = false

    fun Z2.header(configuration: TableConfiguration) =
        div {
            if (configuration.fixHeaderHeight) addClass("table-header-cell-fix-height")
            text { label }
            sortSign = sortSign()
            span("table-resize-handle") {
                onMouseDown(::onResizeMouseDown)
            }
            onClick(::onClick)
        }

    fun Z2.sortSign() =
        div {
            div("table-sort-sign") {  }
        }

    open var renderer: Z2.(row: T) -> Unit = { row ->
        text { format(row) }
    }

    /**
     * [Table] calls this method whenever [Table.setData] runs.
     */
    open fun onTableSetData() {

    }

    fun gridTemplate(): String {
        return "minmax(${min}px, $max)"
    }

    open fun render(cell: Z2, index: Int, row: T) {
        cell.renderer(row)
    }

    open fun onClick(event: Event) {
        if (beenResized) {
            beenResized = false
            return
        }

        sortAscending = !sortAscending

        if (!table.isInitialized) return

        sort()

        table.columns.forEach {
            it.sortSign.addClass("hidden")
        }

        sortSign.removeClass("ascending", "descending")
        sortSign.addClass("table-sort-sign-container")
        if (sortAscending) {
            sortSign.addClass("ascending")
        } else {
            sortSign.addClass("descending")
        }

        sortSign.removeClass("hidden")

        table.filter()
        table.render()
    }

    open fun onResizeMouseDown(event: MouseEvent) {
        beingResized = true
        beenResized = true
        lastX = event.clientX

        table.columns.forEach {
            if (it.id != id) it.element.addClass("table-other-being-resized")
        }

        element.addClass("table-being-resized")
        table.outerContainer.addClass("no-select")

        window.addEventListener("mouseup", mouseUpWrapper)
        window.addEventListener("mousemove", mouseMoveWrapper)
    }

    val mouseUpWrapper = { event: Event -> onMouseUp(event) }

    open fun onMouseUp(event: Event) {
        beingResized = false
        lastX = 0

        table.columns.forEach {
            it.element.removeClass("table-other-being-resized")
        }

        element.removeClass("table-being-resized")
        table.outerContainer.removeClass("no-select")

        window.removeEventListener("mouseup", mouseUpWrapper)
        window.removeEventListener("mousemove", mouseMoveWrapper)

        event.preventDefault()
    }

    val mouseMoveWrapper = { event: Event -> onMouseMove(event) }

    open fun onMouseMove(event: Event) {
        if (!beingResized) return

        event as MouseEvent
        event.preventDefault() // prevents text select during column resize

        window.requestAnimationFrame {

            val distance = event.clientX - lastX
            lastX = event.clientX

            size = max(min, if (size.isNaN()) element.clientWidth.toDouble() else size + distance)

            val tableWidth = table.tableElement.clientWidth
            var sumWidth = 0.0

            table.columns.forEach {
                if (it.size.isNaN()) {
                    it.size = it.element.clientWidth.toDouble()
                }
                sumWidth += it.size
            }

            // When the table is smaller than the sum of colum widths, use 1 fraction for the
            // last column. When larger, use exact pixel widths for each column.

            val template = if (sumWidth >= tableWidth || table.columns.size == 0) {
                table.columns.joinToString(" ") { "${it.size}px" }
            } else {
                table.columns.subList(0, table.columns.size - 1).joinToString(" ") { "${it.size}px" } + " 1fr"
            }

            table.tableElement.style.width = sumWidth.px
            table.tableElement.style.setProperty("grid-template-columns", template)

            table.fullData.forEach { it.height = null }
        }
    }

    protected fun findParentOffset(): Int {
        var offset = 0
        var current = element.offsetParent as? HTMLElement
        while (current != null) {
            if (current.offsetLeft != 0) offset += current.offsetLeft
            current = current.offsetParent as? HTMLElement
        }
        return offset
    }

    /**
     * Converts the cell value into a string.
     */
    open fun format(row: T): String {
        return ""
    }

    /**
     * Sorts the table data by this column.
     */
    open fun sort() {

    }

    /**
     * Checks if this column of the given row matches the given string or not.
     */
    open fun matches(row: T, string: String?): Boolean {
        return false
    }

    open fun exportCsvHeader(): String {
        return label
    }

    open fun exportCsv(row: T): String {
        return ""
    }

}