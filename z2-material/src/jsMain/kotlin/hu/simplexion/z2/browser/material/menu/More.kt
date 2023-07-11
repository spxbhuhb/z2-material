package hu.simplexion.z2.browser.material.menu

import hu.simplexion.z2.browser.material.basicIcons
import hu.simplexion.z2.browser.material.html.Z2
import hu.simplexion.z2.browser.material.html.grid
import hu.simplexion.z2.browser.material.icon.actionIcon
import hu.simplexion.z2.browser.material.popup.Popup
import hu.simplexion.z2.browser.material.popup.Popup.Companion.popup
import kotlinx.dom.addClass

class More(
    val inline: Boolean = false,
    val builder: Z2.() -> Unit
) {

    lateinit var icon: Z2
    lateinit var popup: Popup

    companion object {
        fun Z2.more(inline: Boolean = false, builder: Z2.() -> Unit) = with(this) { More(inline, builder).apply { main() } }
    }

    fun Z2.main() {

        icon = actionIcon(basicIcons.more, inline = inline, cssClass = "popup-parent") {
            popup.toggle()
        }

        popup = icon.popup(300.0, 112.0) {
            grid {
                addClass("menu")
                builder()
            }
        }
    }

}