package hu.simplexion.z2.browser.material.demo.components

import hu.simplexion.z2.browser.material.button.textButton
import hu.simplexion.z2.browser.material.demo.strings
import hu.simplexion.z2.browser.material.html.Z2
import hu.simplexion.z2.browser.material.html.grid
import hu.simplexion.z2.browser.material.html.gridAutoRows
import hu.simplexion.z2.browser.material.snackbar.Snackbar.Companion.snackbar

var snackbarClick = 0

fun Z2.snackbarDemo() {
    grid("400px", gap = 16) {
        gridAutoRows = "min-content"

        textButton(strings.snackbar) { snackbar("${strings.snackbar} ${snackbarClick++}") }
    }
}