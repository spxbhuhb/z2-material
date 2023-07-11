package hu.simplexion.z2.browser.material.textfield

import hu.simplexion.z2.browser.material.ComponentState
import hu.simplexion.z2.browser.material.html.Z2
import hu.simplexion.z2.commons.i18n.LocalizedIcon
import hu.simplexion.z2.commons.i18n.LocalizedText

inline fun Z2.outlinedTextField(
    value: String,
    label: LocalizedText? = null,
    supportingText: LocalizedText? = null,
    leadingIcon: LocalizedIcon? = null,
    trailingIcon: LocalizedIcon? = null,
    state : ComponentState = ComponentState.Enabled,
    error : Boolean = false,
    noinline onChange : TextField.(value : String) -> Unit = {  }
): Z2 =
    TextField(
        value,
        label,
        supportingText,
        filled = false,
        outlined = true,
        leadingIcon,
        trailingIcon,
        state,
        error,
        onChange = onChange
    ).apply { main() }.element