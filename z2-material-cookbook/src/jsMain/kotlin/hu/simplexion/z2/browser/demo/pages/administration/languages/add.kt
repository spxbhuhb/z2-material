package hu.simplexion.z2.browser.demo.pages.administration.languages

import hu.simplexion.z2.browser.css.gridGap24
import hu.simplexion.z2.browser.css.p24
import hu.simplexion.z2.browser.css.w400
import hu.simplexion.z2.browser.html.Z2
import hu.simplexion.z2.browser.html.grid
import hu.simplexion.z2.browser.material.basicStrings
import hu.simplexion.z2.browser.material.button.textButton
import hu.simplexion.z2.browser.material.modal.modal
import hu.simplexion.z2.browser.material.textfield.outlinedTextField
import hu.simplexion.z2.commons.util.localLaunch
import hu.simplexion.z2.schematic.runtime.SchematicAccessFunction
import hu.simplexion.z2.schematic.runtime.access.SchematicAccessContext

internal fun add() {
    localLaunch {
        val language = Language()

        modal(w400) {
            title(strings.addLanguage)

            grid(p24, gridGap24) {
                field { language.isoCode }
                field { language.countryCode }
                field { language.nativeName }
            }

            buttons {
                textButton(basicStrings.cancel) { channel.trySend(false) }
                textButton(basicStrings.add) { channel.trySend(true) }
            }
        }.show()
    }
}

@SchematicAccessFunction
fun Z2.field(context : SchematicAccessContext? = null, accessor : () -> Any?) {
    checkNotNull(context)

    val fieldName = context.field.name
    outlinedTextField("", strings.map[fieldName], strings.support[fieldName]) {  }
}