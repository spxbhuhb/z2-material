package hu.simplexion.z2.browser.material.navigation

import hu.simplexion.z2.browser.material.html.Z2
import hu.simplexion.z2.browser.material.html.div
import hu.simplexion.z2.browser.material.html.text
import hu.simplexion.z2.browser.material.icon.icon
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement

fun Z2.navigationRail(builder : Z2.() -> HTMLElement): HTMLDivElement =
    div("navigation-rail-container") {
        builder()
    }

fun Z2.railItem(item: NavigationItem, onClick: () -> Unit): HTMLDivElement =

    div("navigation-rail-item") {

        if (item.label != null) {
            div("navigation-rail-active-indicator-with-text") {
                item.icon?.let { icon(it, cssClass = "navigation-rail-icon-active") }
            }
            div("navigation-rail-label-text-active", "label-medium") {
                text { item.label }
            }
        } else {
            div("navigation-rail-active-indicator-with-no-text") {
                item.icon?.let { icon(it, cssClass = "navigation-rail-icon-active") }
            }
        }

        addEventListener("click", { onClick() })
    }