package hu.simplexion.z2.browser.form

import hu.simplexion.z2.browser.html.Z2
import hu.simplexion.z2.browser.material.button.textButton
import hu.simplexion.z2.browser.material.textfield.outlinedTextField
import hu.simplexion.z2.browser.util.label
import hu.simplexion.z2.browser.util.uniqueNodeId
import hu.simplexion.z2.commons.i18n.LocalizedText
import hu.simplexion.z2.schematic.runtime.Schematic
import hu.simplexion.z2.schematic.runtime.SchematicAccessFunction
import hu.simplexion.z2.schematic.runtime.access.SchematicAccessContext
import org.w3c.dom.events.Event

/**
 * An input for the schematic field accessed by [accessor]. The actual input depends on
 * the type of the field.
 */
@SchematicAccessFunction
fun Z2.field(context : SchematicAccessContext? = null, @Suppress("UNUSED_PARAMETER") accessor : () -> Any?) {
    checkNotNull(context)

    val field = context.field
    val label = field.label()

    outlinedTextField("", label, label.support) {
        context.schematic.schematicChange(context.field, it)
    }
}

/**
 * A text button that is disabled when [schematic] is invalid.
 */
fun Z2.submitTextButton(schematic: Schematic<*>, title : LocalizedText, onClick : (event : Event) -> Unit) =
    textButton(title, onClick).also { button ->
        button.isDisabled = schematic.isValid
        schematic.schematicAddListener(uniqueNodeId) { thisRef, _  ->
            button.isDisabled = !thisRef.schematicSchema.validate(thisRef).valid
        }
    }