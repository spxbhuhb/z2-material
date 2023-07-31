import hu.simplexion.z2.browser.css.titleLarge
import hu.simplexion.z2.browser.demo.material.*
import hu.simplexion.z2.browser.demo.pages.adminStrings
import hu.simplexion.z2.browser.demo.pages.loginStrings
import hu.simplexion.z2.browser.demo.strings
import hu.simplexion.z2.browser.html.Z2
import hu.simplexion.z2.browser.html.div
import hu.simplexion.z2.browser.layout.Content
import hu.simplexion.z2.browser.layout.defaultLayout
import hu.simplexion.z2.browser.layout.defaultLayoutContent
import hu.simplexion.z2.browser.layout.defaultLayoutHeader
import hu.simplexion.z2.browser.material.navigation.NavigationItem
import hu.simplexion.z2.browser.material.navigation.drawerItem
import hu.simplexion.z2.browser.material.navigation.navigationDrawer
import hu.simplexion.z2.browser.material.px
import hu.simplexion.z2.browser.routing.*
import hu.simplexion.z2.commons.i18n.LocalizedText

fun main() {

    with(routing) {
        this += buttonRoute
        start()
    }

    with(Content) {
        defaultLayout {
            logo()
            defaultLayoutHeader {}
            nav()
            defaultLayoutContent {}
        }
    }
}

val nav = listOf(
    nav(strings.button, buttonRoute),
    nav(strings.calendar, buttonRoute),
    nav(strings.card, buttonRoute),
    nav(strings.container, buttonRoute),
    nav(strings.menu, buttonRoute),
    nav(strings.modal, buttonRoute),
    nav(strings.navigationDrawer, buttonRoute),
    nav(strings.popup, buttonRoute),
    nav(strings.snackbar, buttonRoute),
    nav(strings.switch, buttonRoute),
    nav(strings.textField, buttonRoute),
    nav(strings.table, buttonRoute),
    nav(loginStrings.login, buttonRoute),
    nav(adminStrings.administration, buttonRoute)
)

private fun nav(label: LocalizedText, route: Route) =
    NavigationItem(null, label, route) { routing.open(it.route) }

private fun Z2.nav() =
    navigationDrawer {
        for (item in nav) {
            drawerItem(item)
        }
    }


//private fun Z2.content() {
//
//
//    // This runs 'content' again when the location root changes. That
//    // happens when the user navigates to '/' or the first segment of
//    // the URL changes. Examples: '/a' changes to '/b' or '/a/1'
//    // changes to '/b/1'. This does not trigger a change when '/a/1'
//    // changes to '/a/2'.
//
//    dependsOn { locationRoot.next }
//
//    val next = locationRoot.next ?: return
//
//    // when next is null we are on the home page
//
//    routing(next) {
//        when (next.text) {
//            "button" -> buttonDemo()
//            "calendar" -> calendarDemo()
//            "card" -> cardDemo()
//            "container" -> containerDemo()
//            "menu" -> menuDemo()
//            "modal" -> modalDemo()
//            "navigationDrawer" -> navigationDrawerDemo()
//            "popup" -> popupDemo()
//            "snackbar" -> snackbarDemo()
//            "switch" -> switchDemo()
//            "textField" -> textFieldDemo()
//            "table" -> tableDemo()
//            "login" -> loginDemo()
//            "administration" -> adminDemo()
//            else -> div { text { "TODO" } }
//        }
//    }
//
//}

fun Z2.logo =
    div(titleLarge) {
        style.display = "flex"
        style.alignItems = "center"
        style.paddingLeft = 28.px
        style.paddingTop = 8.px
        text { strings.demoTitle }
    }
