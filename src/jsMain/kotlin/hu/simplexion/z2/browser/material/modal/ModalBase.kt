/*
 * Copyright © 2020-2021, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
package hu.simplexion.z2.browser.material.modal

import hu.simplexion.z2.browser.material.html.Z2
import hu.simplexion.z2.browser.material.html.div
import hu.simplexion.z2.browser.material.html.text
import hu.simplexion.z2.commons.i18n.LocalizedText
import kotlinx.browser.document
import kotlinx.coroutines.channels.Channel
import kotlinx.dom.addClass

/**
 * @property  channel    Receives output of the modal dialog (if there is one).
 *                       Pass a nullable type if the dialog may be closed without
 *                       output.
 */
open class ModalBase<T : Any?> {

    protected val channel = Channel<T>(1)
    protected val element : Z2 = document.createElement("div") as Z2

    init {
        element.addClass("modal")
    }

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

        Modals += element
        val value = channel.receive()
        Modals -= element

        return value
    }

}