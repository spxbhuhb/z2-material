package hu.simplexion.z2.browser.material.popup

import hu.simplexion.z2.browser.material.html.Z2
import hu.simplexion.z2.browser.material.html.div
import kotlinx.browser.document

val popups : Z2 = document.body!!.div("global-popup-container") { }