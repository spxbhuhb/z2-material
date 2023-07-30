import hu.simplexion.z2.browser.calendar.year
import hu.simplexion.z2.browser.demo.material.*
import hu.simplexion.z2.browser.demo.pages.login
import hu.simplexion.z2.browser.demo.pages.loginStrings
import hu.simplexion.z2.browser.demo.strings
import hu.simplexion.z2.browser.demo.table.tableDemo
import hu.simplexion.z2.browser.html.Z2
import hu.simplexion.z2.browser.html.div
import hu.simplexion.z2.browser.html.gridRow
import hu.simplexion.z2.browser.layout.Content
import hu.simplexion.z2.browser.layout.defaultLayout
import hu.simplexion.z2.browser.layout.defaultLayoutContent
import hu.simplexion.z2.browser.layout.defaultLayoutHeader
import hu.simplexion.z2.browser.material.navigation.NavigationItem
import hu.simplexion.z2.browser.material.navigation.drawerItem
import hu.simplexion.z2.browser.material.navigation.navigationDrawer
import hu.simplexion.z2.commons.i18n.LocalizedText
import kotlinx.datetime.DayOfWeek

private fun render(item: NavigationItem) {
    with(Content) {
        this.clear()
        defaultLayout {
            nav().apply { gridRow = "1/span2" }
            defaultLayoutHeader {

            } // top app bar
            defaultLayoutContent {
                content(item)
            }
        }
    }
}

private fun Z2.nav() =
    navigationDrawer {
        nav(strings.button)
        nav(strings.calendar)
        nav(strings.card)
        nav(strings.menu)
        nav(strings.modal)
        nav(strings.popup)
        nav(strings.snackbar)
        nav(strings.switch)
        nav(strings.textField)
        nav(strings.table)
        nav(loginStrings.login)
    }

private fun Z2.content(item: NavigationItem) {
    when (item.label) {
        strings.button -> div { buttonDemo() }
        strings.calendar -> year(2023, DayOfWeek.MONDAY)
        strings.card -> div { cardDemo() }
        strings.menu -> div { menuDemo() }
        strings.modal -> div { modalDemo() }
        strings.popup -> div { popupDemo() }
        strings.snackbar -> div { snackbarDemo() }
        strings.switch -> div { switchDemo() }
        strings.textField -> div { textFieldDemo() }
        strings.table -> tableDemo()
        loginStrings.login -> div { login() }
        else -> div { text { "TODO" } }
    }
}

private fun Z2.nav(label: LocalizedText) =
    drawerItem(NavigationItem(null, label) { render(it) })

fun main() {
    render(NavigationItem(null, strings.table))
}