package hu.simplexion.z2.browser.material.demo.pages

import hu.simplexion.z2.browser.material.button.filledButton
import hu.simplexion.z2.browser.material.button.textButton
import hu.simplexion.z2.browser.material.html.*
import hu.simplexion.z2.browser.material.px
import hu.simplexion.z2.browser.material.textfield.outlinedTextField
import hu.simplexion.z2.commons.i18n.LocalizedTextStore
import hu.simplexion.z2.commons.util.UUID

object loginStrings : LocalizedTextStore(UUID("72c9ec55-0e66-4181-96f9-d9009b03712e")) {
    val loginSupport by "Z2 Material Demo Teszt Rendszer"
    val login by "Azonosítás"
    val account by "Felhasználónév"
    val password by "Jelszó"
    val forgottenPassword by "Elfelejtett jelszó"
    val help by "Segítség"
    val privacy by "Adatvédelem"
    val term by "Szabályok"
    val english by "English"
    val registration by "Regisztráció"
}

fun Z2.login() {
    grid("min-content", gap = 8) {
        gridAutoRows = "min-content"

        div {
            style.padding = 24.px
            style.border = "1px solid lightgray"
            style.borderRadius = "8px"
            grid("400px", gap = 32) {
                gridAutoRows = "min-content"

                div("title-small", "justify-self-center") {
                    text { loginStrings.loginSupport }
                }

                div("title-large", "justify-self-center") {
                    text { loginStrings.login }
                }

                grid("1fr", "min-content min-content", 24) {
                    outlinedTextField("", loginStrings.account)
                    outlinedTextField("", loginStrings.password).apply { input.type = "password" }
                }

                grid("min-content 1fr min-content", "min-content") {
                    style.position = "relative"
                    style.left = "-8px"
                    textButton(loginStrings.forgottenPassword) { }
                    textButton(loginStrings.registration) { }
                    filledButton(loginStrings.login) { }
                }
            }
        }

        grid("1fr repeat(3, min-content)", "min-content", 16) {
            textButton(loginStrings.english) { }
            textButton(loginStrings.help) { }
            textButton(loginStrings.privacy) { }
            textButton(loginStrings.term) { }
        }
    }
}