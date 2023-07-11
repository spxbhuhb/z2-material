package hu.simplexion.z2.browser.material.button

import hu.simplexion.z2.browser.material.html.Z2
import hu.simplexion.z2.browser.material.html.div
import hu.simplexion.z2.browser.material.html.text
import hu.simplexion.z2.browser.material.icon.icon
import hu.simplexion.z2.commons.i18n.LocalizedIcon
import hu.simplexion.z2.commons.i18n.LocalizedText
import org.w3c.dom.events.Event

fun Z2.iconButton(
    icon: LocalizedIcon,
    hint: LocalizedText,
    size : Int = 24,
    weight : Int = 400,
    fill : Int = 0,
    onClick : (event : Event) -> Unit
) =

    div("icon-button", "primary-text") {
        div("icon-button-active-indicator-with-text", "primary-border", "tooltip") {
            icon(icon, size, weight, fill)
            div("plain-tooltip", "body-small") { text { hint } }
        }

        handlers(onClick)
    }