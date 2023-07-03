package hu.simplexion.z2.browser.material.html

import kotlinx.browser.document
import kotlinx.dom.addClass
import kotlinx.dom.appendText
import org.w3c.dom.*
import org.w3c.dom.events.Event

fun Z2.grid(
    columnTemplate: String? = null,
    rowTemplate: String? = null,
    gap: Int? = null,
    builder: Z2.() -> Unit
): HTMLDivElement =
    (document.createElement("div") as HTMLDivElement).also {
        append(it)
        it.style.display = "grid"
        rowTemplate?.let { s -> it.style.setProperty("grid-template-rows", s) }
        columnTemplate?.let { s -> it.style.setProperty("grid-template-columns", s) }
        gap?.let { i -> it.style.setProperty("grid-gap", "${i}px") }
        it.builder()
    }

var Z2.gridTemplateRows: String
    get() = style.getPropertyValue("grid-template-rows")
    set(value) {
        style.setProperty("grid-template-rows", value)
    }

var Z2.gridTemplateColumns: String
    get() = style.getPropertyValue("grid-template-columns")
    set(value) {
        style.setProperty("grid-template-columns", value)
    }

var Z2.gridAutoColumns: String
    get() = style.getPropertyValue("grid-auto-columns")
    set(value) {
        style.setProperty("grid-auto-columns", value)
    }

var Z2.gridAutoRows: String
    get() = style.getPropertyValue("grid-auto-rows")
    set(value) {
        style.setProperty("grid-auto-rows", value)
    }

var Z2.rowGap: String
    get() = style.getPropertyValue("row-gap")
    set(value) {
        style.setProperty("row-gap", value)
    }

var Z2.columnGap: String
    get() = style.getPropertyValue("column-gap")
    set(value) {
        style.setProperty("column-gap", value)
    }

var Z2.gridColumn: String
    get() = style.getPropertyValue("grid-column")
    set(value) {
        style.setProperty("grid-column", value)
    }

var Z2.gridRow: String
    get() = style.getPropertyValue("grid-row")
    set(value) {
        style.setProperty("grid-row", value)
    }