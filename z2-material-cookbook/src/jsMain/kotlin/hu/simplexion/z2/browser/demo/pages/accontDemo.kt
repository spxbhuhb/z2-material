package hu.simplexion.z2.browser.demo.pages

import hu.simplexion.z2.browser.css.labelMedium
import hu.simplexion.z2.browser.css.pb24
import hu.simplexion.z2.browser.css.titleLarge
import hu.simplexion.z2.browser.html.*
import hu.simplexion.z2.browser.material.card.outlinedCard
import hu.simplexion.z2.browser.material.px
import hu.simplexion.z2.browser.material.switch.switch
import hu.simplexion.z2.commons.i18n.LocalizedTextStore
import hu.simplexion.z2.commons.util.UUID

object accountStrings : LocalizedTextStore(UUID("72c9ec55-0e66-4181-96f9-d9009b03712e")) {
    val account by "Fiók"
    val basicData by "Alapadatok"
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
}

fun Z2.accountDemo() =
    div {

        div(titleLarge, pb24) {
            text { accountStrings.account }
        }

        basicInfo()
        status()
    }

fun Z2.basicInfo() =
    outlinedCard(accountStrings.basicData) {
        div {
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
    }

fun Z2.status() =
    outlinedCard(accountStrings.basicData) {
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

        div {
            style.display = "grid"
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
