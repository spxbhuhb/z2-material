/*
 * Copyright © 2020-2021, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
package hu.simplexion.z2.browser.material.modal

import hu.simplexion.z2.browser.html.Z2
import hu.simplexion.z2.browser.html.div
import hu.simplexion.z2.commons.i18n.LocalizedText
import kotlinx.browser.document
import kotlinx.coroutines.channels.Channel
import org.w3c.dom.HTMLElement

/**
 * @property  channel    Receives output of the modal dialog (if there is one).
 *                       Pass a nullable type if the dialog may be closed without
 *                       output.
 */
@Suppress("UNCHECKED_CAST")
open class ModalBase<T : Any?>(
    builder : ModalBase<T>.() -> Unit
) : Z2(
    null,
    document.createElement("div") as HTMLElement,
    arrayOf("modal"),
    builder as (Z2.() -> Unit)
) {

    val channel = Channel<T>(1)

    fun Z2.title(title : LocalizedText) =
        div {
            addClass("modal-headline", "headline-small")
            text { title }
        }

    fun Z2.supportingText(builder : () -> String) : Z2 =
        div("modal-content", "body-medium") {
            text { builder() }
        }

    fun Z2.buttons(builder : Z2.() -> Unit) : Z2 =
        div("modal-buttons") {
            builder()
        }

    open suspend fun show(): T {
        //
        // WARNING
        //
        // Before you modify or override this method check:
        //
        // - Modals.unaryPlus
        // - Modals.unaryMinus
        // - TODO Form In Modal cookbook recipe, especially the back button confirmation (form Zakadabar)
        //

        Modals += this
        val value = channel.receive()
        Modals -= this

        return value
    }

}