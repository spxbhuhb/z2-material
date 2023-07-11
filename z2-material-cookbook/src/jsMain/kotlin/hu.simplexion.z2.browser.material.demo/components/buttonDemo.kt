package hu.simplexion.z2.browser.material.demo.components

import hu.simplexion.z2.browser.material.basicIcons
import hu.simplexion.z2.browser.material.button.*
import hu.simplexion.z2.browser.material.demo.strings
import hu.simplexion.z2.browser.material.html.*
import hu.simplexion.z2.browser.material.snackbar.Snackbar.Companion.snackbar

fun Z2.buttonDemo() {
    grid {
        gridTemplateColumns = "min-content"
        gridAutoRows = "min-content"
        gridGap = "16px"

        filledButton(strings.filledButton) { snackbar(strings.filledButton) }
        textButton(strings.textButton) { snackbar(strings.textButton) }
        smallDenseTextButton(strings.smallDenseTextButton) { snackbar(strings.smallDenseTextButton) }
        iconButton(basicIcons.settings, strings.settings) { snackbar(strings.settings) }
        segmentedButton(
            strings.segment1 to false,
            strings.segment2 to true,
            strings.segment3 to false
        ) { snackbar("${strings.click}: $it")}
    }
}