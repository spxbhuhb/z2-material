/*
 * Copyright © 2020-2023, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
package hu.simplexion.z2.browser.material.modal

import hu.simplexion.z2.browser.material.html.Z2
import kotlinx.browser.document
import kotlinx.dom.addClass
import kotlinx.dom.removeClass
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement

/**
 * Contains modal windows and shows them over the normal content.
 */
object Modals {

    class Layer(
        val child: Z2,
        val background: Z2
    )

    val layers = mutableListOf<Layer>()

    val element = document.createElement("div") as HTMLDivElement

    /**
     * A cleanup hook. Called when the modal is removed from the list of modals
     * (typically when the modal is closed).
     */
    var cleanup : Z2.() -> Unit = {  }

    init {
        document.body?.appendChild(element)
        element.addClass("hidden", "persistent")
    }

    operator fun plusAssign(child: Z2) {
        element.removeClass("hidden")

        val layer = Layer(child, document.createElement("div") as HTMLElement)

        with(layer.background) {
            addClass("modal-background")
            appendChild(child)
            style.zIndex = (1900 + layers.size).toString()
        }

        layers += layer

        element.appendChild(layer.background)
    }

    operator fun minusAssign(child: Z2) {
        val index = layers.indexOfFirst { it.child == child }
        if (index == -1) return

        while (layers.size > index) {
            layers.last().also {
                it.background.cleanup()
                it.background.remove()
            }
            layers.removeLast()
        }

        if (layers.isEmpty()) element.addClass("hidden")
    }

}