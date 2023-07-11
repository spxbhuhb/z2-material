package hu.simplexion.z2.browser.material.snackbar

import hu.simplexion.z2.browser.material.basicIcons
import hu.simplexion.z2.browser.material.button.textButton
import hu.simplexion.z2.browser.material.html.Z2
import hu.simplexion.z2.browser.material.html.div
import hu.simplexion.z2.browser.material.html.text
import hu.simplexion.z2.browser.material.icon.actionIcon
import hu.simplexion.z2.browser.material.io
import hu.simplexion.z2.commons.i18n.LocalizedText
import hu.simplexion.z2.commons.i18n.Token
import kotlinx.browser.document
import kotlinx.coroutines.delay
import kotlinx.dom.addClass
import org.w3c.dom.events.Event

class Snackbar(
    val message: String,
    val label: LocalizedText?,
    val icon: Boolean,
    val action: ((event : Event) -> Unit)?
) {

    companion object {
        fun snackbar(
            message: LocalizedText,
            label: LocalizedText? = null,
            icon: Boolean = false,
            action: ((event : Event) -> Unit)? = null
        ) {
            Snackbars += Snackbar(message.toString(), label, icon, action)
        }

        fun snackbar(
            message: String,
            label: Token<String>? = null,
            icon: Boolean = false,
            action: ((event : Event) -> Unit)? = null
        ) {
            Snackbars += Snackbar(message, label, icon, action)
        }
    }
    val element = (document.createElement("div") as Z2)

    init {
        with(element) {
            addClass("snackbar")

            div("body-medium") { text { message } }

            action?.let {
                label?.let { textButton(label, action) }
            }

            if (icon) {
                actionIcon(basicIcons.close) { Snackbars -= this@Snackbar }
            }
        }
    }

    fun show() {
        io {
            this.element.addClass("fade-in-bottom")
            Snackbars.element.appendChild(this.element)
            delay(3000)
            Snackbars -= this
        }
    }
}