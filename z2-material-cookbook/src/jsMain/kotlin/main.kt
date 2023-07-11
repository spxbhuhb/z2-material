import hu.simplexion.z2.browser.material.calendar.year
import hu.simplexion.z2.browser.material.demo.components.*
import hu.simplexion.z2.browser.material.demo.pages.login
import hu.simplexion.z2.browser.material.demo.pages.loginStrings
import hu.simplexion.z2.browser.material.demo.strings
import hu.simplexion.z2.browser.material.html.Z2
import hu.simplexion.z2.browser.material.html.div
import hu.simplexion.z2.browser.material.html.gridRow
import hu.simplexion.z2.browser.material.html.text
import hu.simplexion.z2.browser.material.layout.defaultLayout
import hu.simplexion.z2.browser.material.layout.defaultLayoutContent
import hu.simplexion.z2.browser.material.layout.defaultLayoutHeader
import hu.simplexion.z2.browser.material.navigation.NavigationItem
import hu.simplexion.z2.browser.material.navigation.drawerItem
import hu.simplexion.z2.browser.material.navigation.navigationDrawer
import hu.simplexion.z2.commons.i18n.LocalizedText
import kotlinx.browser.document
import kotlinx.datetime.DayOfWeek
import kotlinx.dom.clear

val content = (document.createElement("div") as Z2).also {
    document.body!!.appendChild(it)
    it.style.height = "100vh"
    it.style.width = "100vw"
    it.style.padding = "0"
    it.style.margin = "0"
}

private fun render(item: NavigationItem) {
    with(content) {
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
        nav(loginStrings.login)
    }

private fun Z2.content(item: NavigationItem): Z2 =
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
        loginStrings.login -> div { login() }
        else -> div { text { "TODO" } }
    }

private fun Z2.nav(label: LocalizedText) =
    drawerItem(NavigationItem(null, label) { render(it) })

fun main() {
    render(NavigationItem(null, loginStrings.login))
}