package hu.simplexion.z2.browser.material.demo.pages

import hu.simplexion.z2.browser.html.*
import hu.simplexion.z2.browser.material.button.filledButton
import hu.simplexion.z2.browser.material.button.textButton
import hu.simplexion.z2.browser.material.fr
import hu.simplexion.z2.browser.material.px
import hu.simplexion.z2.browser.material.textfield.outlinedTextField
import hu.simplexion.z2.commons.i18n.LocalizedTextStore
import hu.simplexion.z2.commons.util.UUID
import org.w3c.dom.HTMLInputElement

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
    grid() {
        gridTemplateColumns = "min-content"
        gridAutoRows = "min-content"
        gridGap = 8.px

        div {
            style.padding = 24.px
            style.border = "1px solid lightgray"
            style.borderRadius = "8px"
            grid {
                gridTemplateColumns = 400.px
                gridAutoRows = "min-content"
                gridGap = 32.px

                div("title-small", "justify-self-center") {
                    text { loginStrings.loginSupport }
                }

                div("title-large", "justify-self-center") {
                    text { loginStrings.login }
                }

                grid {
                    gridTemplateColumns = 1.fr
                    gridTemplateRows = "min-content min-content"
                    gridGap = 24.px
                    outlinedTextField("", loginStrings.account)
                    outlinedTextField("", loginStrings.password)
                        .apply { (input.htmlElement as HTMLInputElement).type = "password" }
                }

                grid {
                    gridTemplateColumns = "min-content 1fr min-content"
                    gridTemplateRows = "min-content"
                    gridGap = 24.px

                    style.position = "relative"
                    style.left = "-8px"
                    textButton(loginStrings.forgottenPassword) { }
                    textButton(loginStrings.registration) { }
                    filledButton(loginStrings.login) { }
                }
            }
        }

        grid {
            gridTemplateColumns = "1fr repeat(3, min-content)"
            gridTemplateRows = "min-content"
            gridGap = 16.px

            textButton(loginStrings.english) { }
            textButton(loginStrings.help) { }
            textButton(loginStrings.privacy) { }
            textButton(loginStrings.term) { }
        }
    }
}