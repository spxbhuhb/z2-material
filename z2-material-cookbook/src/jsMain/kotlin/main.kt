import hu.simplexion.z2.browser.css.titleLarge
import hu.simplexion.z2.browser.demo.calendar.calendarDemo
import hu.simplexion.z2.browser.demo.layout.containerDemo
import hu.simplexion.z2.browser.demo.material.*
import hu.simplexion.z2.browser.demo.pages.adminDemo
import hu.simplexion.z2.browser.demo.pages.adminStrings
import hu.simplexion.z2.browser.demo.pages.loginDemo
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
import hu.simplexion.z2.browser.routing.BrowserRouter
import hu.simplexion.z2.browser.routing.Renderer
import hu.simplexion.z2.browser.routing.Router

@Suppress("unused")
object mainRouter : BrowserRouter() {

    // @formatter:off
    val button           by render(strings.button)           { buttonDemo() }
    val calendar         by render(strings.calendar)         { calendarDemo() }
    val card             by render(strings.card)             { cardDemo() }
    val container        by render(strings.container)        { containerDemo() }
    val menu             by render(strings.menu)             { menuDemo() }
    val modal            by render(strings.modal)            { modalDemo() }
    val navigationDrawer by render(strings.navigationDrawer) { navigationDrawerDemo() }
    val popup            by render(strings.popup)            { popupDemo() }
    val snackbar         by render(strings.snackbar)         { snackbarDemo() }
    val switch           by render(strings.switch)           { switchDemo() }
    val textField        by render(strings.textField)        { textFieldDemo() }
    val table            by render(strings.table)            { tableDemo() }

    val pages            by pagesRouter
    // @formatter:on

    override fun notFound(receiver: Z2, path: List<String>) {
        receiver.div {
            text { strings.pageNotFound }
        }
    }
}

@Suppress("unused")
object pagesRouter : Router<Z2>() {
    val login by render(loginStrings.login) { loginDemo() }
    val administration by render(adminStrings.administration) { adminDemo() }
}

fun main() {
    with(Content) {
        defaultLayout {
            logo()
            defaultLayoutHeader {}
            nav()
            defaultLayoutContent {
                mainRouter.receiver = this
            }
        }
    }
    mainRouter.start()
}

private fun Z2.nav() =
    navigationDrawer {
        mainRouter.targets.forEach { target ->
            if (target is Renderer<*>) {
                drawerItem(NavigationItem(target.icon, target.label) { mainRouter.open(target) })
            }
        }

        pagesRouter.targets.forEach { target ->
            if (target is Renderer<*>) {
                drawerItem(NavigationItem(target.icon, target.label) { mainRouter.open(target) })
            }
        }
    }

fun Z2.logo() =
    div(titleLarge) {
        style.display = "flex"
        style.alignItems = "center"
        style.paddingLeft = 28.px
        style.paddingTop = 8.px
        text { strings.demoTitle }
    }
