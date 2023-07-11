package hu.simplexion.z2.browser.material.demo.components

import hu.simplexion.z2.browser.material.button.textButton
import hu.simplexion.z2.browser.material.demo.strings
import hu.simplexion.z2.browser.material.html.Z2
import hu.simplexion.z2.browser.material.modal.confirm

fun Z2.modalDemo() {
    textButton(strings.confirmDialog) { confirm(strings.confirmDialog, strings.confirmMessage) { } }
}