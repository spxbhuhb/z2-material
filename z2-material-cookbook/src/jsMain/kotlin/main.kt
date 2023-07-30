import hu.simplexion.z2.browser.css.titleLarge
import hu.simplexion.z2.browser.demo.calendar.calendarDemo
import hu.simplexion.z2.browser.demo.layout.containerDemo
import hu.simplexion.z2.browser.demo.material.*
import hu.simplexion.z2.browser.demo.pages.login
import hu.simplexion.z2.browser.demo.pages.loginStrings
import hu.simplexion.z2.browser.demo.strings
import hu.simplexion.z2.browser.demo.table.tableDemo
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
import hu.simplexion.z2.commons.i18n.LocalizedText

private fun render(item: NavigationItem) {
    with(Content) {
        this.clear()
        defaultLayout {
            div(titleLarge) {
                style.display = "flex"
                style.alignItems = "center"
                style.paddingLeft = 28.px
                style.paddingTop = 8.px
                text { strings.demoTitle }
            }
            defaultLayoutHeader {
// top app bar
            }
            nav()
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
        nav(strings.container)
        nav(strings.menu)
        nav(strings.modal)
        nav(strings.navigationDrawer)
        nav(strings.popup)
        nav(strings.snackbar)
        nav(strings.switch)
        nav(strings.textField)
        nav(strings.table)
        nav(loginStrings.login)
    }

private fun Z2.content(item: NavigationItem) {
    when (item.label) {
        strings.button -> buttonDemo()
        strings.calendar -> calendarDemo()
        strings.card -> cardDemo()
        strings.container -> containerDemo()
        strings.menu -> menuDemo()
        strings.modal -> modalDemo()
        strings.navigationDrawer -> navigationDrawerDemo()
        strings.popup -> popupDemo()
        strings.snackbar -> snackbarDemo()
        strings.switch -> switchDemo()
        strings.textField -> textFieldDemo()
        strings.table -> tableDemo()
        loginStrings.login -> login()
        else -> div { text { "TODO" } }
    }
}

private fun Z2.nav(label: LocalizedText) =
    drawerItem(NavigationItem(null, label) { render(it) })

fun main() {
    render(NavigationItem(null, strings.navigationDrawer))
}