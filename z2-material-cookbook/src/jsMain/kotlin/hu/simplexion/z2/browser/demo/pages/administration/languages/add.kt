package hu.simplexion.z2.browser.demo.pages.administration.languages

import hu.simplexion.z2.browser.css.gridGap24
import hu.simplexion.z2.browser.css.p24
import hu.simplexion.z2.browser.css.w400
import hu.simplexion.z2.browser.form.field
import hu.simplexion.z2.browser.form.submitTextButton
import hu.simplexion.z2.browser.html.grid
import hu.simplexion.z2.browser.material.basicStrings
import hu.simplexion.z2.browser.material.button.textButton
import hu.simplexion.z2.browser.material.modal.modal
import hu.simplexion.z2.commons.util.localLaunch

internal fun add() {
    localLaunch {
        val language = addModal().show() ?: return@localLaunch
        // Languages.add(language)
    }
}

private fun addModal() =

    modal(w400) {
        val language = Language()

        title(strings.addLanguage)

        grid(p24, gridGap24) {
            field { language.isoCode }
            field { language.countryCode }
            field { language.nativeName }
        }

        buttons {
            textButton(basicStrings.cancel) { channel.trySend(null) }
            submitTextButton(language, basicStrings.add) { channel.trySend(language) }
        }
    }