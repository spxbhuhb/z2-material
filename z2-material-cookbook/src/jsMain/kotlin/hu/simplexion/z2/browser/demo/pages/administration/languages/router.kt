package hu.simplexion.z2.browser.demo.pages.administration.languages

import hu.simplexion.z2.browser.demo.NavRouter
import hu.simplexion.z2.browser.html.Z2Builder

@Suppress("unused")
object languagesRouter : NavRouter() {
    override val label = strings.languages
    override val icon = icons.languages

    override var useParentNav = true

    val add  by render { add() }
    val edit by render { edit() }

    override val default: Z2Builder = { list() }
}