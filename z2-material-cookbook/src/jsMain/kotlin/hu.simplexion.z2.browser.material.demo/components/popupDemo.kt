package hu.simplexion.z2.browser.material.demo.components

import hu.simplexion.z2.browser.material.basicIcons
import hu.simplexion.z2.browser.material.button.textButton
import hu.simplexion.z2.browser.material.card.filledCard
import hu.simplexion.z2.browser.material.demo.strings
import hu.simplexion.z2.browser.material.html.*
import hu.simplexion.z2.browser.material.icon.actionIcon
import hu.simplexion.z2.browser.material.popup.Popup
import hu.simplexion.z2.browser.material.popup.Popup.Companion.popup
import kotlinx.dom.addClass

fun Z2.popupDemo() {
    grid("400px", gap = 16) {
        gridAutoRows = "min-content"

        div {
            var popup: Popup? = null
            textButton(strings.popup) {
                popup?.toggle()
                it.preventDefault()
                it.stopPropagation() // this is necessary for buttons
            }.apply {
                addClass("position-relative", "popup-parent")
                popup = popup { filledCard { text { strings.loremShort } } }
            }
        }

        div {
            var popup: Popup? = null
            actionIcon(basicIcons.settings) {
                popup?.toggle()
            }.apply {
                addClass("position-relative", "popup-parent")
                popup = popup { filledCard { text { strings.loremShort } } }
            }
        }
    }
}