package hu.simplexion.z2.browser.demo.pages.administration.languages

import hu.simplexion.z2.browser.css.gridGap24
import hu.simplexion.z2.browser.css.p24
import hu.simplexion.z2.browser.css.w400
import hu.simplexion.z2.browser.html.grid
import hu.simplexion.z2.browser.material.basicStrings
import hu.simplexion.z2.browser.material.button.textButton
import hu.simplexion.z2.browser.material.modal.modal
import hu.simplexion.z2.browser.material.textfield.outlinedTextField
import hu.simplexion.z2.commons.util.localLaunch

internal fun add() {
    localLaunch {
        var isoCode: String
        var countryCode: String
        var nativeName: String

        modal(w400) {
            title(strings.addLanguage)

            grid(p24, gridGap24) {
                outlinedTextField("", strings.isoCode, strings.isoCodeSupport) { isoCode = it }
                outlinedTextField("", strings.countryCode, strings.countryCodeSupport) { countryCode = it }
                outlinedTextField("", strings.nativeName, strings.nativeNameSupport) { nativeName = it }
            }

            buttons {
                textButton(basicStrings.cancel) { channel.trySend(false) }
                textButton(basicStrings.add) { channel.trySend(true) }
            }
        }.show()
    }
}



