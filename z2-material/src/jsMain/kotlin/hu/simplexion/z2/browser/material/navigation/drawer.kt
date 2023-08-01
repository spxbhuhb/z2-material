package hu.simplexion.z2.browser.material.navigation

import hu.simplexion.z2.browser.html.Z2
import hu.simplexion.z2.browser.html.div
import hu.simplexion.z2.browser.html.gridColumn
import hu.simplexion.z2.browser.html.onClick
import hu.simplexion.z2.browser.material.icon.icon
import hu.simplexion.z2.browser.routing.RoutingTarget

fun Z2.navigationDrawer(vararg targets : RoutingTarget<Z2>, scrollAutoHide : Boolean = true) =
    navigationDrawer(targets.toList(), scrollAutoHide)

fun Z2.navigationDrawer(targets: Collection<RoutingTarget<Z2>>, scrollAutoHide : Boolean = true) =
    div("navigation-drawer-container") {
        if (scrollAutoHide) addClass("scroll-auto-hide")
        for (target in targets) {
            if (target.icon == null && target.label == null) continue
            drawerItem(target)
        }
    }

fun Z2.navigationDrawer(scrollAutoHide : Boolean = true, builder: Z2.() -> Unit) =
    div("navigation-drawer-container") {
        if (scrollAutoHide) addClass("scroll-auto-hide")
        builder()
    }

fun Z2.drawerItem(target : RoutingTarget<Z2>) {
    drawerItem(NavigationItem(target.icon, target.label) { target.open(target) })
}

fun Z2.drawerItem(item: NavigationItem, onClick: (() -> Unit)? = null) =

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