package hu.simplexion.z2.browser.material.demo.components

import hu.simplexion.z2.browser.html.*
import hu.simplexion.z2.browser.material.button.textButton
import hu.simplexion.z2.browser.material.demo.strings
import hu.simplexion.z2.browser.material.px
import hu.simplexion.z2.browser.material.snackbar.snackbar

var snackbarClick = 0

fun Z2.snackbarDemo() {
    grid {
        gridTemplateColumns = 400.px
        gridAutoRows = "min-content"
        gridGap = 16.px


        textButton(strings.snackbar) { snackbar("${strings.snackbar} ${snackbarClick++}") }
    }
}