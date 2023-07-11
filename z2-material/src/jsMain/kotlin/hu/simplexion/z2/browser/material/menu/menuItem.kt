package hu.simplexion.z2.browser.material.menu

import hu.simplexion.z2.browser.material.html.*
import hu.simplexion.z2.browser.material.icon.icon
import hu.simplexion.z2.commons.i18n.LocalizedIcon
import hu.simplexion.z2.commons.i18n.LocalizedText

fun Z2.menuItem(
    index: Int,
    icon: LocalizedIcon,
    label: LocalizedText,
    trailing: (Z2.() -> Unit)? = null,
    onClick: () -> Unit
) {
    val row = index.toString()

    div("menu-item") {
        gridRow = row
        gridColumn = "1"
        icon(icon, cssClass = "menu-icon")
        on("click") { onClick() }
    }

    div("menu-item", "label-large", "justify-self-start") {
        gridRow = row
        gridColumn = "2"
        div {
            text { label }
        }
        on("click") { onClick() }
    }

    div("menu-item", "menu-trailing") {
        gridRow = row
        gridColumn = "3"
        if (trailing != null) trailing()
        on("click") { onClick() }
    }

    div("menu-row") {
        gridRow = row
        gridColumn = "1/4"
        on("click") { onClick() }
    }
}
