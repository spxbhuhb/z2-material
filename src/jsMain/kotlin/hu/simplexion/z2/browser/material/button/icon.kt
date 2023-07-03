package hu.simplexion.z2.browser.material.button

import hu.simplexion.z2.browser.material.html.Z2
import hu.simplexion.z2.browser.material.html.div
import hu.simplexion.z2.browser.material.html.on
import hu.simplexion.z2.browser.material.html.text
import hu.simplexion.z2.browser.material.icon.icon
import hu.simplexion.z2.commons.i18n.Token
import kotlinx.dom.addClass

fun Z2.iconButton(
    icon: Token<String>,
    hint: Token<String>,
    size : Int = 24,
    weight : Int = 400,
    fill : Int = 0,
    onClick : () -> Unit
) =

    div("icon-button", "primary-text") {
        div("icon-button-active-indicator-with-text", "primary-border", "tooltip") {
            icon(icon, size, weight, fill)
            div("plain-tooltip", "body-small") { text { hint } }
        }

        on("click") { onClick() }
    }