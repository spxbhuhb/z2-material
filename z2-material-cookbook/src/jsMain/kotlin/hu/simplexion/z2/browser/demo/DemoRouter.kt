package hu.simplexion.z2.browser.demo

import defaultLayout
import hu.simplexion.z2.browser.html.Z2
import hu.simplexion.z2.browser.material.navigation.navigationDrawer
import hu.simplexion.z2.browser.routing.RoutedRenderer
import hu.simplexion.z2.browser.routing.Router
import hu.simplexion.z2.commons.i18n.LocalizedIcon
import hu.simplexion.z2.commons.i18n.LocalizedText

open class DemoRouter : Router<Z2>() {

    val nav : Z2.() -> Unit = { navigationDrawer(targets) }

    override fun render(label: LocalizedText?, icon: LocalizedIcon?, renderFun: Z2.() -> Unit): RoutedRenderer<Z2> {
        return super.render(label, icon) { defaultLayout(this@DemoRouter, nav, renderFun) }
    }

    override fun default(receiver: Z2, path: List<String>) {
        receiver.defaultLayout(this, nav) {  }
    }

}