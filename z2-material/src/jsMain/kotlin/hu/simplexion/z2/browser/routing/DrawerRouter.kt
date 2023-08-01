package hu.simplexion.z2.browser.routing

import hu.simplexion.z2.browser.html.Z2
import hu.simplexion.z2.browser.material.navigation.navigationDrawer
import hu.simplexion.z2.commons.i18n.LocalizedIcon
import hu.simplexion.z2.commons.i18n.LocalizedText

open class DrawerRouter: Router<Z2>() {

    open val nav: Z2.() -> Unit = { navigationDrawer(targets) }

    override fun default(receiver: Z2, path: List<String>) {
        receiver.layout(this, label, nav) { }
    }

    override fun render(label: LocalizedText?, icon: LocalizedIcon?, renderFun: Z2.() -> Unit): Renderer<Z2> {
        return super.render(label, icon) {
            layout(this@DrawerRouter, label, { navigationDrawer(targets) }, renderFun)
        }
    }

    companion object {
        var layout : Z2.(router : Router<Z2>, title: LocalizedText?, nav: Z2.() -> Unit, content: Z2.() -> Unit) -> Unit = { _, _, _, _ -> }
    }

}