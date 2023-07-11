package hu.simplexion.z2.browser.material.demo.components

import hu.simplexion.z2.browser.material.html.Z2
import hu.simplexion.z2.browser.material.html.grid
import hu.simplexion.z2.browser.material.html.gridAutoRows
import hu.simplexion.z2.browser.material.switch.switch

fun Z2.switchDemo() {
    grid("400px", gap = 16) {
        gridAutoRows = "min-content"
        switch(true) { }
        switch(false) { }
    }
}