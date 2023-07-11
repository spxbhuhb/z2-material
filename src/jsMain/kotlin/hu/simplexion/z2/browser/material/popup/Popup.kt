/*
 * Copyright © 2020-2021, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
package hu.simplexion.z2.browser.material.popup

import hu.simplexion.z2.browser.material.html.Z2
import hu.simplexion.z2.browser.material.html.on
import kotlinx.browser.document
import kotlinx.dom.addClass
import org.w3c.dom.events.FocusEvent

open class Popup(
    val anchorElement : Z2,
    val minHeight: Double = 100.0,
    val minWidth: Double = 100.0,
    builder : Z2.() -> Unit
) {

    companion object {
        fun Z2.popup(
            minHeight: Double = 100.0,
            minWidth: Double = 100.0,
            builder : Z2.() -> Unit
        ) = Popup(this, minHeight, minWidth, builder)
    }

    var shown: Boolean = false

    val element : Z2 = (document.createElement("div") as Z2)

    init {
        with(element) {
            addClass("popup")

            builder()

            on("focusout") { event ->
                event as FocusEvent

                val relatedTarget = event.relatedTarget

                if (relatedTarget is Z2 && this@Popup.element.contains(relatedTarget)) {
                    //onClick(relatedTarget)
                } else {
                    hide()
                }
            }
        }
    }

    open fun toggle() {
        if (shown) {
            hide()
        } else {
            show()
        }
    }

    open fun show() {
        shown = true
        popups.appendChild(this.element)
        alignPopup(this.element, anchorElement, minHeight, minWidth)
        element.tabIndex = 0
        element.focus()
    }

    fun hide() {
        if (shown) {
            shown = false
            element.remove()
        }
    }

}