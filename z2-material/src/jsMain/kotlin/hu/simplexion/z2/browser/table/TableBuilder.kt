package hu.simplexion.z2.browser.table

import hu.simplexion.z2.browser.css.alignSelfCenter
import hu.simplexion.z2.browser.css.positionRelative
import hu.simplexion.z2.browser.css.titleLarge
import hu.simplexion.z2.browser.css.whiteSpaceNoWrap
import hu.simplexion.z2.browser.html.*
import hu.simplexion.z2.browser.material.basicIcons
import hu.simplexion.z2.browser.material.basicStrings
import hu.simplexion.z2.browser.material.button.outlinedIconButton
import hu.simplexion.z2.browser.material.icon.icon
import hu.simplexion.z2.browser.material.px
import hu.simplexion.z2.commons.i18n.LocalizedText
import org.w3c.dom.HTMLInputElement

class TableBuilder<T> : TableConfiguration() {

    val columns = mutableListOf<TableColumnBuilder<T>>()

    var query: (() -> List<T>)? = null

    var rowId: ((row : T) -> String)? = null

    fun column(builder : TableColumnBuilder<T>.() -> Unit) {
        columns += TableColumnBuilder<T>().apply { builder () }
    }

    override var search: Boolean = false
        set(value) {
            field = value
            if (value && searchBar == null) {
                if (titleBar == null) titleBar = { titleBar() }
                searchBar = { searchBar() }
            }
        }

    override var titleText: LocalizedText? = null
        set(value) {
            field = value
            title = true
            if (titleBar == null) titleBar = { titleBar() }
        }

    fun Z2.textTitle() {
        div(titleLarge, whiteSpaceNoWrap) {
            text { titleText }
        }
    }

    fun Z2.titleBar(): Z2 =
        grid("table-title-bar") {
            if (!title) return@grid

            grid(alignSelfCenter) {
                gridTemplateColumns = "min-content min-content"
                gridTemplateRows = "min-content"
                gridGap = 16.px
                div(alignSelfCenter) {
                    textTitle()
                }
                if (add) outlinedIconButton(basicIcons.add, basicStrings.add) { }
            }

            div(positionRelative) {
                if (search) {
                    searchBar?.let { it() }
                }
            }

            div(alignSelfCenter) {
                if (export) outlinedIconButton(basicIcons.export, basicStrings.export) { }
            }
        }

    fun Z2.searchBar() =
        div("table-search-bar-container") {
            val container = this
            icon(basicIcons.search, cssClass = "table-search-bar-leading-icon")
            input("table-search-bar-input", "body-medium", "text-select") {
                (htmlElement as HTMLInputElement).placeholder = basicStrings.searchHint.toString()
                onFocus { container.addClass("table-search-bar-active")}
                onBlur { container.removeClass("table-search-bar-active")}
            }
            icon(basicIcons.filter, cssClass = "table-search-bar-trailing-icon")
        }
}