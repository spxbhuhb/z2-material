package hu.simplexion.z2.browser.material.demo.components

import hu.simplexion.z2.browser.material.basicIcons
import hu.simplexion.z2.browser.material.demo.strings
import hu.simplexion.z2.browser.material.html.Z2
import hu.simplexion.z2.browser.material.html.grid
import hu.simplexion.z2.browser.material.html.text
import hu.simplexion.z2.browser.material.menu.More.Companion.more
import hu.simplexion.z2.browser.material.menu.menuItem


fun Z2.menuDemo() {
    grid("max-content min-content", gap = 16) {
        text { "Menu with 1 item, normal icon" }
        more {
            menuItem(1, basicIcons.search, strings.menuItem1) { }
        }
    }

    grid("max-content min-content", gap = 16) {
        text { "Menu with 2 items, inline icon" }
        more(inline = true) {
            menuItem(1, basicIcons.search, strings.menuItem1) { }
            menuItem(2, basicIcons.search, strings.menuItem2) { }
        }
    }

}