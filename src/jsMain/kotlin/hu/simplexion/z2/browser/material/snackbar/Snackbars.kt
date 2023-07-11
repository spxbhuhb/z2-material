/*
 * Copyright © 2020-2023, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
package hu.simplexion.z2.browser.material.snackbar

import kotlinx.browser.document
import org.w3c.dom.HTMLDivElement

/**
 * Contains modal windows and shows them over the normal content.
 */
object Snackbars {

    val element = document.createElement("div") as HTMLDivElement

    val waiting = mutableListOf<Snackbar>()
    var active : Snackbar? = null

    init {
        document.body?.appendChild(element)
    }

    operator fun plusAssign(child: Snackbar) {
        waiting += child
        show()
    }

    operator fun minusAssign(child: Snackbar) {
        element.removeChild(child.element)
        active = null
        show()
    }

    fun show() {
        if (active != null) return
        if (waiting.isEmpty()) return

        active = waiting.removeFirst()
        active?.show()
    }
}