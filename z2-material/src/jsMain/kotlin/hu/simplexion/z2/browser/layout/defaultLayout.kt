package hu.simplexion.z2.browser.layout

import hu.simplexion.z2.browser.html.Z2
import hu.simplexion.z2.browser.html.div

fun Z2.defaultLayout(
    builder: Z2.() -> Unit
): Z2 =
    div("layout-default") {
        builder()
    }

fun Z2.defaultLayoutHeader(
    builder: Z2.() -> Unit
): Z2 =
    div {
        addClass("surface", "on-surface-text", "layout-default-header")
        builder()
    }

fun Z2.defaultLayoutContent(
    builder: Z2.() -> Unit
): Z2 =
    div {
        addClass("surface", "on-surface-text", "layout-default-content")
        builder()
    }