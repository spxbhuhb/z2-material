package hu.simplexion.z2.browser.material.textfield

import hu.simplexion.z2.browser.material.html.Z2
import hu.simplexion.z2.commons.i18n.LocalizedText

fun Z2.filledTextField(value : String, placeholder : LocalizedText) : Z2 {
    return TextField(value, placeholder = placeholder, filled = true){  }.apply { main() }.element
}

fun Z2.outlinedTextField(value : String, placeholder : LocalizedText) : Z2 {
    return TextField(value, placeholder = placeholder, outlined = true){  }.apply { main() }.element
}