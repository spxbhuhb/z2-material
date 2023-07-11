/*
 * Copyright © 2020-2021, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
package hu.simplexion.z2.browser.material.modal

import hu.simplexion.z2.browser.material.button.textButton
import hu.simplexion.z2.commons.i18n.LocalizedText

open class Confirm(
    open val title: LocalizedText,
    open val message: LocalizedText,
    open val noLabel: LocalizedText,
    open val yesLabel: LocalizedText
) : ModalBase<Boolean>() {

    init {
        with (element) {
            title(this@Confirm.title)
            supportingText { message.toString() }
            buttons {
                textButton(noLabel) { channel.trySend(false) }
                textButton(yesLabel) { channel.trySend(true) }
            }
        }
    }

}