package hu.simplexion.z2.browser.demo.pages

import hu.simplexion.z2.browser.css.*
import hu.simplexion.z2.browser.demo.DemoRouter
import hu.simplexion.z2.browser.html.*
import hu.simplexion.z2.browser.layout.low
import hu.simplexion.z2.browser.layout.lowest
import hu.simplexion.z2.browser.material.card.outlinedCard
import hu.simplexion.z2.browser.material.px
import hu.simplexion.z2.browser.material.switch.switch
import hu.simplexion.z2.commons.i18n.LocalizedTextStore
import hu.simplexion.z2.commons.util.UUID

object accountStrings : LocalizedTextStore(UUID("72c9ec55-0e66-4181-96f9-d9009b03712e")) {
    val account by "Fiók"
    val basicInfo by "Alapadatok"
    val email by "E-mail cím"
    val name by "Név"
    val birthday by "Születési dátum"
    val type by "Típus"
    val password by "Jelszó"
    val verification by "Megerősítés"
    val status by "Állapot"
    val locked by "Zárolt"
    val validated by "Ellenőrzött"
    val expired by "Lejárt"
    val anonymized by "Anonimizált"
    val lastLoginSuccess by "Utolsó sikeres belépés"
    val loginSuccessCount by "Sikeres belépések száma"
    val lastLoginFail by "Utolsó sikertelen belépés"
    val loginFailCount by "Sikertelen belépések száma"
    val journal by "Napló"
}

// @formatter:off
@Suppress("unused")
object accountRouter : DemoRouter() {
    override val label = accountStrings.account

    val basicInfo  by render(accountStrings.basicInfo)    { basicInfo() }
    val password   by render(accountStrings.password)     { passwordChange() }
    val status     by render(accountStrings.status)       { status() }
    val history    by render(accountStrings.journal)      { accountDemo() }

    override fun default(receiver: Z2, path: List<String>) { open(basicInfo) }
}
// @formatter:on

fun Z2.accountDemo() =
    low {
        div(displayFlex, h42, mb8, alignItemsCenter, titleLarge) {
            div { text { accountStrings.account } }
        }

        lowest {
            basicInfo()
            status()
        }
    }

fun Z2.basicInfo() =
    div(borderOutline) {
        style.display = "grid"
        gridTemplateColumns = "200px 600px"
        gridAutoRows = "min-content"
        gridGap = 16.px

        div(labelMedium) { text { accountStrings.name } }
        div { text { "Tóth István Zoltán" } }

        div(labelMedium) { text { accountStrings.birthday } }
        div { text { "1977.08.15." } }

        div(labelMedium) { text { accountStrings.type } }
        div { text { "ügy résztvevő" } }
    }


fun Z2.status() =
    outlinedCard(accountStrings.status) {
        grid {
            gridTemplateColumns = "1fr min-content"
            gridGap = 8.px

            div(labelMedium) { text { accountStrings.locked } }
            switch(false) { }

            div(labelMedium) { text { accountStrings.validated } }
            switch(false) { }

            div(labelMedium) { text { accountStrings.expired } }
            switch(false) { }

            div(labelMedium) { text { accountStrings.anonymized } }
            switch(false) { }
        }

        grid {
            gridTemplateColumns = "200px 1fr"
            gridAutoRows = "min-content"
            gridGap = 16.px

            div(labelMedium) { text { accountStrings.lastLoginSuccess } }
            div { text { "2023.07.28. 11:18:23" } }

            div(labelMedium) { text { accountStrings.lastLoginFail } }
            div { text { "2023.07.28. 11:18:23" } }

            div(labelMedium) { text { accountStrings.loginFailCount } }
            div { text { "2023.07.28. 11:18:23" } }
        }

    }

fun Z2.passwordChange() {

}