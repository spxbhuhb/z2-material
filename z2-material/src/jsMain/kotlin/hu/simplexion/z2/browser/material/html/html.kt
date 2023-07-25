package hu.simplexion.z2.browser.material.html

import kotlinx.browser.document
import kotlinx.dom.addClass
import kotlinx.dom.appendText
import org.w3c.dom.*
import org.w3c.dom.events.Event

typealias Z2 = HTMLElement

inline fun Z2.text(builder: () -> Any?) {
    builder()?.let { appendText(it.toString()) }
}

fun <T : Z2> Z2.build(element: T, classes: Array<out String>, builder: T.() -> Unit): T {
    if (classes.isNotEmpty()) element.addClass(*classes)
    append(element)
    element.builder()
    return element
}

fun Z2.on(event: String, handler: (event: Event) -> Unit): HTMLElement =
    this.apply { addEventListener(event, handler) }

fun Z2.div(vararg classes: String, builder: HTMLDivElement.() -> Unit): HTMLDivElement =
    build(document.createElement("div") as HTMLDivElement, classes, builder)

fun Z2.pre(vararg classes: String, builder: HTMLPreElement.() -> Unit): HTMLPreElement =
    build(document.createElement("pre") as HTMLPreElement, classes, builder)

fun Z2.input(vararg classes: String, builder: HTMLInputElement.() -> Unit): HTMLInputElement =
    build(document.createElement("input") as HTMLInputElement, classes, builder)

fun Z2.span(vararg classes: String, builder: HTMLSpanElement.() -> Unit): HTMLSpanElement =
    build(document.createElement("span") as HTMLSpanElement, classes, builder)

fun Z2.table(vararg classes: String, builder: HTMLTableElement.() -> Unit): HTMLTableElement =
    build(document.createElement("table") as HTMLTableElement, classes, builder)

fun Z2.thead(vararg classes: String, builder: HTMLTableSectionElement.() -> Unit): HTMLTableSectionElement =
    build(document.createElement("thead") as HTMLTableSectionElement, classes, builder)

fun Z2.th(vararg classes: String, builder: HTMLTableCellElement.() -> Unit): HTMLTableCellElement =
    build(document.createElement("th") as HTMLTableCellElement, classes, builder)

fun Z2.tbody(vararg classes: String, builder: HTMLTableSectionElement.() -> Unit): HTMLTableSectionElement =
    build(document.createElement("tbody") as HTMLTableSectionElement, classes, builder)

fun Z2.tr(vararg classes: String, builder: HTMLTableRowElement.() -> Unit): HTMLTableRowElement =
    build(document.createElement("tr") as HTMLTableRowElement, classes, builder)

fun Z2.td(vararg classes: String, builder: HTMLTableCellElement.() -> Unit): HTMLTableCellElement =
    build(document.createElement("td") as HTMLTableCellElement, classes, builder)
