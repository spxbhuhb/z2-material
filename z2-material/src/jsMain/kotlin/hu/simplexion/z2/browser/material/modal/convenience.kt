package hu.simplexion.z2.browser.material.modal

import hu.simplexion.z2.browser.material.basicStrings
import hu.simplexion.z2.browser.util.io
import hu.simplexion.z2.commons.i18n.LocalizedText

suspend fun confirm(
    title: LocalizedText,
    message: LocalizedText,
    noLabel: LocalizedText = basicStrings.cancel,
    yesLabel: LocalizedText = basicStrings.yes
) : Boolean {
    return Confirm(title, message, noLabel, yesLabel).show()
}

fun confirm(
    title: LocalizedText,
    message: LocalizedText,
    noLabel: LocalizedText = basicStrings.cancel,
    yesLabel: LocalizedText = basicStrings.yes,
    action : suspend () -> Unit
) {
   io {
        if (Confirm(title, message, noLabel, yesLabel).show()) {
            action()
        }
    }
}