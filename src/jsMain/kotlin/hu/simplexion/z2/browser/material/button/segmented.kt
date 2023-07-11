package hu.simplexion.z2.browser.material.button

import hu.simplexion.z2.browser.material.html.Z2
import hu.simplexion.z2.browser.material.html.div
import hu.simplexion.z2.browser.material.html.on
import hu.simplexion.z2.browser.material.html.text
import hu.simplexion.z2.commons.i18n.LocalizedText

fun Z2.segmentedButton(vararg segments: Pair<LocalizedText, Boolean>, onClick: (selected: LocalizedText) -> Unit) =
    div("segmented-button-container") {
        for (segment in segments) {
            div("segmented-button", "label-large", if (segment.second) "selected" else "unselected") {
                text { segment.first }
                on("mousedown") { it.preventDefault() } // to avoid focus
                on("click") { onClick(segment.first) }
            }
        }
    }