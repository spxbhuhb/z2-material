package hu.simplexion.z2.browser.material.modal

import hu.simplexion.z2.browser.material.basicStrings
import hu.simplexion.z2.browser.material.button.textButton
import hu.simplexion.z2.browser.util.applySuspend
import hu.simplexion.z2.commons.i18n.LocalizedText

fun <T : Any?> modal(builder: ModalBase<T>.() -> Unit): ModalBase<T> =
    ModalBase(builder)

fun confirm(
    title: LocalizedText,
    message: LocalizedText,
    noLabel: LocalizedText = basicStrings.cancel,
    yesLabel: LocalizedText = basicStrings.yes,
    action: (suspend () -> Unit)? = null
) {
    modal {
        title(title)
        supportingText { message.toString() }
        buttons {
            textButton(noLabel) { channel.trySend(false) }
            textButton(yesLabel) { channel.trySend(true) }
        }
    }.applySuspend {
        if (show() && action != null) action()
    }
}