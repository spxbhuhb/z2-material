package hu.simplexion.z2.browser.material.navigation

import hu.simplexion.z2.browser.material.html.Z2
import hu.simplexion.z2.browser.material.html.div
import hu.simplexion.z2.browser.material.html.gridColumn
import hu.simplexion.z2.browser.material.html.text
import hu.simplexion.z2.browser.material.icon.icon
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement

fun Z2.navigationDrawer(builder: Z2.() -> HTMLElement): HTMLDivElement =
    div("navigation-drawer-container") {
        builder()
    }

fun Z2.drawerItem(item: NavigationItem, onClick: (() -> Unit)? = null): HTMLDivElement =

    div("navigation-drawer-item") {

        item.icon?.let {
            div("navigation-drawer-icon") {
                icon(it, cssClass = "navigation-rail-icon")
            }
        }

        div("navigation-drawer-label", "label-large", "align-self-center") {
            text { item.label }
            if (item.icon == null) gridColumn = "1/span2"
        }

        div("navigation-drawer-badge", "align-self-center") {
            text { item.badgeLabel }
        }

        addEventListener("click", {
            if (onClick != null) onClick()
            item.onClick?.invoke(item)
        })
    }