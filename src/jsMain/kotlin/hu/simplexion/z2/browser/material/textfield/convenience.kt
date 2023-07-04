package hu.simplexion.z2.browser.material.textfield

import hu.simplexion.z2.browser.material.html.Z2
import hu.simplexion.z2.commons.i18n.LocalizedIcon
import hu.simplexion.z2.commons.i18n.LocalizedText

fun Z2.filledTextField(value : String, label : LocalizedText) : Z2 {
    return TextField(value, label = label, filled = true){  }.apply { main() }.element
}

fun Z2.filledTextField(value : String, leadingIcon : LocalizedIcon, trailingIcon : LocalizedIcon, label : LocalizedText) : Z2 {
    return TextField(value, leadingIcon, trailingIcon, label = label, filled = true){  }.apply { main() }.element
}

fun Z2.outlinedTextField(value : String, label : LocalizedText) : Z2 {
    return TextField(value, label = label, outlined = true){  }.apply { main() }.element
}

fun Z2.outlinedTextField(value : String, leadingIcon : LocalizedIcon, trailingIcon : LocalizedIcon, label : LocalizedText) : Z2 {
    return TextField(value, leadingIcon, trailingIcon, label = label, outlined = true){  }.apply { main() }.element
}
