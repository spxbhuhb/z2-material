package hu.simplexion.z2.browser.demo.pages.administration.languages

import hu.simplexion.z2.browser.css.alignSelfCenter
import hu.simplexion.z2.browser.css.borderOutline
import hu.simplexion.z2.browser.css.p0
import hu.simplexion.z2.browser.css.titleLarge
import hu.simplexion.z2.browser.html.*
import hu.simplexion.z2.browser.layout.container
import hu.simplexion.z2.browser.layout.lowest
import hu.simplexion.z2.browser.material.basicStrings
import hu.simplexion.z2.browser.material.button.textButton
import hu.simplexion.z2.browser.material.px
import hu.simplexion.z2.browser.table.table

internal fun Z2.list() =
    lowest(borderOutline) {
        class Row(
            val v1: String,
            val v2: Int
        )

        val data = (1..50).map {
            Row("s-$it", it)
        }

        container(p0, scroll = false) {
            style.background = "transparent"

            table<Row> {
                titleBar = { header() }
                search = true
                add = true
                export = true

                rowId = { it.v1 }
                query = { data }

                column {
                    label = basicStrings.name.toString()
                    render = { text { it.v1 } }
                    comparator = { a, b -> a.v2.compareTo(b.v2) }
                }

                column {
                    label = strings.nativeName.toString()
                    render = { text { it.v2 } }
                    comparator = { a, b -> a.v2.compareTo(b.v2) }
                }

                column {
                    label = basicStrings.actions.toString()
                    render = { text { "Actions"} }
                }

            }
        }
    }


private fun Z2.header(): Z2 =
    grid {
        gridTemplateRows = "60px"
        gridTemplateColumns = "1fr min-content"

        grid {
            gridTemplateColumns = "min-content min-content"
            gridTemplateRows = "min-content"
            gridGap = 16.px
            div(alignSelfCenter, titleLarge) {
                text { strings.languages }
            }
        }

        grid {
            textButton(strings.addLanguage) { add() }
        }

    }