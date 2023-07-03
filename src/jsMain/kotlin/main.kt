import hu.simplexion.z2.browser.material.button.*
import hu.simplexion.z2.browser.material.calendar.year
import hu.simplexion.z2.browser.material.html.Z2
import hu.simplexion.z2.browser.material.html.grid
import hu.simplexion.z2.browser.material.navigation.NavigationItem
import hu.simplexion.z2.browser.material.navigation.drawerItem
import hu.simplexion.z2.browser.material.navigation.navigationDrawer
import hu.simplexion.z2.commons.i18n.Token
import hu.simplexion.z2.commons.i18n.asToken
import kotlinx.browser.document
import kotlinx.datetime.DayOfWeek
import kotlinx.dom.clear

object strings {
    val buttons = "Button".asToken()
    val filledButton = "Filled Button".asToken()
    val calendar = "Calendar".asToken()
    val textButton = "Text Button".asToken()
    val smallDenseTextButton = "Small Dense Text Button".asToken()
    val settings = "Settings".asToken()
    val segment1 = "Segment 1".asToken()
    val segment2 = "Segment 2".asToken()
    val segment3 = "Segment 3".asToken()
}

object icons {
    val settings = "settings".asToken()
}

val content = (document.createElement("div") as Z2)

fun Z2.nav(label: Token<String>, builder: Z2.() -> Unit) =
    drawerItem(
        NavigationItem(null, label) {
            content.clear()
            content.builder()
        }
    )


fun main() {
    with(document.body !!) {
        grid("min-content 1fr", "1fr", 16) {
            style.width = "100vw"
            style.height = "100vh"
            navigationDrawer {
                nav(strings.buttons) { buttons() }
                nav(strings.calendar) { year(2023, DayOfWeek.MONDAY) }
            }
            appendChild(content)
        }
    }
}

fun Z2.buttons() {
    filledButton(strings.filledButton)
    textButton(strings.textButton) {  }
    smallDenseTextButton(strings.smallDenseTextButton) { }
    iconButton(icons.settings, strings.settings) {  }
    segmentedButton(
        strings.segment1 to false,
        strings.segment2 to true,
        strings.segment3 to false
    ) {  }
}