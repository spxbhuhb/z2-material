package hu.simplexion.z2.browser.demo.pages.administration.languages

import hu.simplexion.z2.schematic.runtime.Schematic

class Language : Schematic<Language>() {
    val isoCode by string(minLength = 2, maxLength = 2)
    val countryCode by string(minLength = 2, maxLength = 2, pattern = Regex("[A-Z]{2}"))
    val nativeName by string(minLength = 2, blank = false)
}