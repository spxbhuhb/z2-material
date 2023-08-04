package hu.simplexion.z2.browser.material.button

import hu.simplexion.z2.browser.css.labelLarge
import hu.simplexion.z2.browser.css.labelSmall
import hu.simplexion.z2.browser.css.textTransformCapitalize
import hu.simplexion.z2.browser.html.Z2
import hu.simplexion.z2.browser.html.div
import hu.simplexion.z2.browser.html.onClick
import hu.simplexion.z2.browser.html.onMouseDown
import hu.simplexion.z2.commons.i18n.LocalizedText
import org.w3c.dom.events.Event

fun Z2.filledButton(label : LocalizedText, onClick : (event : Event) -> Unit) =
    common(label, onClick, "button-filled", labelLarge, textTransformCapitalize)

fun Z2.textButton(label : LocalizedText, onClick : (event : Event) -> Unit) =
    common(label, onClick, "button-text", labelLarge, textTransformCapitalize)

fun Z2.smallDenseTextButton(label : LocalizedText, onClick : (event : Event) -> Unit) =
    common(label, onClick, "button-text", "dense", labelSmall, textTransformCapitalize)

internal fun Z2.common(label : LocalizedText, onClick : (event : Event) -> Unit, vararg classes : String) =
    div(*classes) {
        htmlElement.tabIndex = 1 // to make tab navigation viable
        text { label }
        handlers(onClick)
    }

internal fun Z2.handlers(onClick : (event : Event) -> Unit) {
    this.onClick(onClick)
    onMouseDown { it.preventDefault() } // to prevent the focus on the button
}