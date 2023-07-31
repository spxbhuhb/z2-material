package hu.simplexion.z2.browser.routing

import hu.simplexion.z2.commons.i18n.LocalizedIcon
import hu.simplexion.z2.commons.i18n.LocalizedText

class Renderer<R>(
    val label: LocalizedText? = null,
    val icon: LocalizedIcon? = null,
    val renderFun: R.() -> Unit
) : RoutingTarget<R> {

    override var parent: Router<R>? = null

    override var relativePath : String = ""

    override fun open(receiver: R, path: List<String>) {
        receiver.renderFun()
    }

}