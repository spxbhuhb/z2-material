package hu.simplexion.z2.browser.demo.pages.administration

import defaultLayout
import hu.simplexion.z2.browser.css.*
import hu.simplexion.z2.browser.demo.NavRouter
import hu.simplexion.z2.browser.html.*
import hu.simplexion.z2.browser.layout.lowest
import hu.simplexion.z2.browser.material.basicStrings
import hu.simplexion.z2.browser.material.button.filledButton
import hu.simplexion.z2.browser.material.card.outlinedCard
import hu.simplexion.z2.browser.material.fr
import hu.simplexion.z2.browser.material.navigation.NavigationItem
import hu.simplexion.z2.browser.material.px

@Suppress("unused")
object administrationRouter : NavRouter() {
    override val label = strings.administration
    override val icon = icons.administration

    // @formatter:off
    val impressum        by render(strings.impressum, icons.impressum)           {  }
    val template         by render(strings.template, icons.template)             {  }
    val languages        by render(strings.languageAndRegion, icons.languages)  {  }

    val account          by render(strings.account, icons.accounts)             {  }
    val roles            by render(strings.role, icons.roles)                   {  }
    val securityPolicy   by render(strings.securityPolicy, icons.securityPolicy) {  }

    val connections      by render(strings.connection, icons.interfaces)         {  }
    val history          by render(strings.history, icons.history)             {  }
    // @formatter:on

    override fun default(receiver: Z2, path: List<String>) {
        receiver.defaultLayout(this, nav) { administration() }
    }
}

fun Z2.administration() =
    lowest(borderOutline) {
        div(titleLarge, pb24) {
            text { strings.administration }
        }

        grid {
            gridTemplateColumns = "repeat(auto-fit, 300px)"
            gridAutoRows = "min-content"
            gridGap = 8.px

            for (item in administrationRouter.targets) {
                adminCard(NavigationItem(item.icon, item.label) { item.open() })
            }
        }
    }

fun Z2.adminCard(item: NavigationItem) {
    outlinedCard(item.label) {
        style.display = "grid"
        gridTemplateColumns = 1.fr
        gridTemplateRows = "min-content 1fr min-content"

        div(bodyMedium) {
            text { item.label?.support }
        }

        div(justifySelfEnd, pt24) {
            filledButton(basicStrings.open) { }
        }
    }
}

fun Z2.accounts() =
    div {
        text { "accounts " }
    }