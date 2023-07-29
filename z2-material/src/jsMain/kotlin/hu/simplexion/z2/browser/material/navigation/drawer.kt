package hu.simplexion.z2.browser.material.navigation

import hu.simplexion.z2.browser.html.Z2
import hu.simplexion.z2.browser.html.div
import hu.simplexion.z2.browser.html.gridColumn
import hu.simplexion.z2.browser.html.onClick
import hu.simplexion.z2.browser.material.icon.icon

fun Z2.navigationDrawer(builder: Z2.() -> Z2): Z2 =
    div("navigation-drawer-container") {
        builder()
    }

fun Z2.drawerItem(item: NavigationItem, onClick: (() -> Unit)? = null): Z2 =

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

        this.onClick {
            if (onClick != null) onClick()
            item.onClick?.invoke(item)
        }
    }