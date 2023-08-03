package hu.simplexion.z2.browser.demo.pages.administration.languagesAndRegions

import hu.simplexion.z2.browser.css.borderOutline
import hu.simplexion.z2.browser.css.p0
import hu.simplexion.z2.browser.demo.NavRouter
import hu.simplexion.z2.browser.html.*
import hu.simplexion.z2.browser.layout.container
import hu.simplexion.z2.browser.layout.low
import hu.simplexion.z2.browser.material.navigation.navigationDrawer
import hu.simplexion.z2.browser.material.px
import hu.simplexion.z2.browser.table.table

// @formatter:off
@Suppress("unused")
object languageAndRegionRouter : NavRouter() {
    override val label = strings.languagesAndRegions
    override val icon = icons.languages

    override val nav: Z2Builder = { navigationDrawer(this@languageAndRegionRouter.parent!!.targets) }
    override val default: Z2Builder = { list() }

    val add     by render { add() }
    val edit    by render { edit() }
}
// @formatter:on

fun Z2.list() =
    low(p0) {
        style.paddingTop = 16.px
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
                titleText = strings.languagesAndRegions
                search = true
                add = true
                export = true

                rowId = { it.v1 }
                query = { data }

                column {
                    label = strings.name.toString()
                    render = { text { it.v1 } }
                    comparator = { a, b -> a.v2.compareTo(b.v2) }
                }

                column {
                    label = strings.nativeName.toString()
                    render = { text { it.v2 } }
                    comparator = { a, b -> a.v2.compareTo(b.v2) }
                }

                column {
                    label = strings.actions.toString()
                    render = { text { "Actions"} }
                }

            }
        }
    }

fun Z2.add() =
    div(borderOutline) {
        style.display = "grid"
        gridTemplateColumns = "200px 600px"
        gridAutoRows = "min-content"
        gridGap = 16.px

    }


fun Z2.edit() =
    low {
    }
