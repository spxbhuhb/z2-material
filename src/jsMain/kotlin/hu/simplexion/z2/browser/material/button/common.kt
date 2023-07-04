package hu.simplexion.z2.browser.material.button

import hu.simplexion.z2.browser.material.html.Z2
import hu.simplexion.z2.browser.material.html.div
import hu.simplexion.z2.browser.material.html.on
import hu.simplexion.z2.browser.material.html.text
import hu.simplexion.z2.commons.i18n.LocalizedText
import kotlinx.dom.addClass

fun Z2.filledButton(label : LocalizedText) =

    div {
        addClass("button-filled", "label-large")
        text { label }
    }

fun Z2.textButton(label : LocalizedText, onClick : () -> Unit) =
    div {
        addClass("button-text", "label-large")
        text { label }
        on("click") { onClick() }
    }

fun Z2.smallDenseTextButton(label : LocalizedText, onClick : () -> Unit) =
    div {
        addClass("button-text", "dense", "label-small")
        text { label }
        on("click") { onClick() }
    }